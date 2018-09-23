# SCDF demo
Collection of sample apps to demo SCDF Streams and Tasks on Spring Cloud Data Flow for PCF.
Tested with SCDF for PCF Server 1.2.0 (SCDF Server v1.6.3)

[Preparation](#Preparation)

[SCDF Stream demo](#SCDF Stream demo)

[SCDF Task demo](#SCDF Task demo)

## Preparation
These steps should be completed before delivering the demo

1. Install SCDF for PCF
    - Download tile: https://network.pivotal.io/products/p-dataflow
    - Installation Instructions: https://docs.pivotal.io/scdf/1-1/installing.html


1. Create SCDF Server Service instance
    ```
    cf cs p-dataflow standard dataflow
    ```


1. Create MySQL Service Instance for Species Database
    ```
    cf cs p.mysql db-small mymysql
    ```

1. Load data into Database
    - One option is to deploy Pivotal MySQL Web tool to manage DB. This is handy now to create the schema and import the data, but also later on to update the tag of the rows.
      - App location: https://github.com/pivotal-cf/PivotalMySQLWeb
      - UI credentials: admin / cfmysqlweb
      - Create schema using `/db/species_nyc_schema.sql` in this repo.
      - Import data using `/db/species_nyc_small.sql` or `/db/species_nyc.sql` in this repo, depending on how much data you want to load.

1. Bulk import stream app starters
    - Stream Apps
      - Get URL from here: https://cloud.spring.io/spring-cloud-stream-app-starters/
      - At the time of the creation of this repo this is the URL used: http://bit.ly/Celsius-SR3-stream-applications-rabbit-maven
      - Go to the space where you created the SCDF server SI, select the Data Flow Server and click on Manage. In the SCDF Dashboard click on Add Applications -> Bulk Import Applications and use the app starters URL.
    - Task Apps
      - Get URL from here: https://cloud.spring.io/spring-cloud-task-app-starters/
      - At the time of the creation of this repo this is the URL used: http://bit.ly/Dearborn-GA-task-applications-maven
      - Bulk upload these apps as you did the Stream ones.

1. Deploy `species-demo` API app
    - We need an endpoint `https://species-app.apps.pcfgcp.jagapps.co/species` for the Stream sink to talk to.
    - TODO: Add app to the repo


## SCDF Stream demo

### Create and Deploy Stream
You can use the Data Flow Server Dashboard or the Shell. In this guide we will use the Shell.

1. Connect to the shell using CF CLI dataflow-shell plugin
    ```
    cf dataflow-shell dataflow
    ```

1. Deploy Custom Apps
    - In this Demo we will use three apps:
      - Source: The `jdbc` app starter.
      - Processor: A custom processor app located in the `/processor-sample` folder of this repo.
      - Sink: A custom sink app located in the `/sink-sample` folder of this repo.
      ![stream_image](./images/stream.png)

    - Create jar files for each of the apps. From each of the above folders, run:
      ```
      mvn clean package
      ```
      - You will need to put them in a public location to be accessed via http/https by the Data Flow Server.
      - For convenience there is a version of these in a public S3 bucket:
        - https://s3.amazonaws.com/jaguilar-releases/processor-sample-0.0.1-SNAPSHOT.jar
        - https://s3.amazonaws.com/jaguilar-releases/sink-sample-0.0.1-SNAPSHOT.jar

    - Register apps in the Data Flow Server
      ```
      app register species-add-state --type processor --uri https://s3.amazonaws.com/jaguilar-releases/processor-sample-0.0.1-SNAPSHOT.jar
      app register species-api --type sink --uri https://s3.amazonaws.com/jaguilar-releases/sink-sample-0.0.1-SNAPSHOT.jar
      ```

1. Deploy Stream
    - Create stream definition with options detailing the three apps we listed above
      ```
      stream create --name jdbc-to-api --definition "jdbc --query='select id,county,category,taxonomy_g,taxonomy_sg,sci_name,common_name from species where tag is NULL' --update='update species set tag=1 where id in (:id)' --outputType=application/json | species-add-state --outputType=application/json | species-api"
      ```
      - The above command sets some key properties for the jdbc source app to know how to query the data, and how to update each row to avoid processing them twice.
      - We also set source and processor to use json format for the payload that gets passed from app to app.
    - Deploy stream
      ```
      stream deploy --name jdbc-to-api --properties "deployer.jdbc.cloudfoundry.services=mymysql"
      ```
      - In the deploy command we set a property indicating the Data Flow server to bind the `jdbc` source app to the `mymysql` Service Instance so that it can read the data with pre-loaded.
      - Once the stream is deployed, SCDF will deploy the 3 apps in PCF and when the source comes up it will start reading all data and passing it on in the stream for processor and later sink to handle the payload.
        - Thanks to the specific queries passed to the `jdbc` app, data will be read only once (After read, all rows are labeled with `tag=1`)
        - Once that happens, which we can check by running `cf logs` for the sink app, we can access the `species-demo` endpoint (https://species-app.apps.pcfgcp.jagapps.co/species) and confirm that we see all the data that is in the Species DB.


## SCDF Task demo

1. Connect to the shell using CF CLI dataflow-shell plugin
    ```
    cf dataflow-shell dataflow
    ```

1. Wipe out data from `species-demo` app
    - Restart the app for the in-memory DB to be wiped. 


1. Create Task
    - Register task in the Data Flow server
      ```
      app register species-jdbc-2-api --type task --uri  https://s3.amazonaws.com/jaguilar-releases/task-sample-0.0.5-SNAPSHOT.jar
      ```

1. Deploy Task
    - Create task with a distinctive name and select the app name you registered as the definition
      ```
      task create --name species-task --definition species-jdbc-2-api
      ```
    - Deploy the task carefully selecting the 2 MySQL Service Instances you need the app to bind to: The SCDF Datastore for the task status and the one required by the business logic of the task with the NYC Species.
      ```
      task launch --name species-task --properties "deployer.species-jdbc-2-api.cloudfoundry.services=relational-cd75dcb0-9f85-4325-a103-1ddde18515b0,mymysql"
      ```
    - Once the task is deployed into PCF, it will read the data form the Species DB and will call the same `species-demo` API endpoint that was called by the Stream sink (https://species-app.apps.pcfgcp.jagapps.co/species)
      - Check that endpoint to confirm that we see all the data that is in the Species DB.
