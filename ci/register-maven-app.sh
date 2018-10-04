#!/usr/bin/env sh

set -e -u

cd scdf-zone/processor-sample

THE_VERSION=$(grep -o '<revision[^"]*' pom.xml | sed -e 's/<revision>\(.*\)<\/revision>/\1/')

echo "Registering processor-sample ${THE_VERSION}"

# Get token. client_id is cf to emulate cf CLI calls
# output=$(curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k -v)
curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k -v > response
# token=$(jq -r '.access_token' <<< "${output}")
token=$(jq -r '.access_token' response)

# Replace `<ROUTE>` with the route of SCDF-server running in CF.
curl \
	-X \
	POST "https://dataflow-cd75dcb0-9f85-4325-a103-1ddde18515b0.apps.pcfgcp.jagapps.co/apps/processor/species-add-state?force=true" \
		-d "uri=https://s3.amazonaws.com/jaguilar-releases/processor-sample-${THE_VERSION}.jar" \
		-H "Authorization: bearer "${token} \
	  -k -v
