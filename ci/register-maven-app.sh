#!/usr/bin/env sh

set -e -u

cd scdf-zone/processor-sample

THE_VERSION=$(grep -o '<version[^"]*' pom.xml | sed -e 's/<version>\(.*\)<\/version>/\1/')

echo "Registering proc1 ${THE_VERSION}"

# Get token. client_id is cf to emulate cf CLI calls
output=$(curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k)
token=$(jq -r '.access_token' <<< "$output")

# Replace `<ROUTE>` with the route of SCDF-server running in CF.
curl \
	-X \
	POST "http://dataflow-9c27ceda-b077-427f-b5fa-c07f606dc5a8.apps.pcfgcp.jagapps.co/apps/processor/proc1?force=true" \
		-d "uri=https://s3.amazonaws.com/jaguilar-releases/processor-sample-${THE_VERSION}.jar" \
		-H "Authorization: bearer "${token} \
	  -v
