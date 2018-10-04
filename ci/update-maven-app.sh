#!/usr/bin/env sh

set -e -u

cd scdf-zone/processor-sample

THE_VERSION=$(grep -o '<revision[^"]*' pom.xml | sed -e 's/<revision>\(.*\)<\/revision>/\1/')

echo "Updating demo stream in SCDF for PCF with ${THE_VERSION} of Maven artifact"

# Get token. client_id is cf to emulate cf CLI calls
# output=$(curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k -v)
curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k -v > response
# token=$(jq -r '.access_token' <<< "${output}")
token=$(jq -r '.access_token' response)

# Replace `<ROUTE>` with the route of SCDF-server running in CF.
curl \
	-X \
	POST "https://dataflow-cd75dcb0-9f85-4325-a103-1ddde18515b0.apps.pcfgcp.jagapps.co/streams/deployments/update/jdbc-to-api" \
    -d '{"updateProperties":{"version.procs":"'"${THE_VERSION}"'"},"releaseName":"jdbc-to-api","packageIdentifier":{"packageName":"jdbc-to-api"}}' \
    -H "Content-Type: application/json" \
		-H "Authorization: bearer "${token} \
		-k -v
