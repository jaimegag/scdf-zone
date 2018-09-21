# SCDF demo
Collection of sample apps to demo SCDF Streams and Tasks on Spring Cloud Data Flow for PCF.
Tested with SCDF for PCF Server 1.2.0 (SCDF Server v1.6.3)

## SCDF Stream demo

### Preparation
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
  - Get URL from here: https://cloud.spring.io/spring-cloud-stream-app-starters/
  - At the time of the creation of this repo this is the URL used: http://bit.ly/Celsius-SR3-stream-applications-rabbit-maven
  - Go to the space where you created the SCDF server SI, select the Data Flow Server and click on Manage. In the SCDF Dashboard click on Add Applications -> Bulk Import Applications and use the app starters URL.

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
    - Create stream definition with options
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
