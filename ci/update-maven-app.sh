#!/usr/bin/env sh

set -e -u

cd scdf-zone/processor-sample

THE_VERSION=$(grep -o '<version[^"]*' pom.xml | sed -e 's/<version>\(.*\)<\/version>/\1/')

echo "Updating demo stream in SCDF for PCF with ${THE_VERSION} of Maven artifact"

# Get token. client_id is cf to emulate cf CLI calls
# output=$(curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k -v)
curl -X POST "https://login.sys.pcfgcp.jagapps.co/oauth/token" -d "grant_type=password&password=gonative&scope=&username=jaime&client_id=cf&client_secret=" -H "Content-Type: application/x-www-form-urlencoded" -H "Accept: application/json" -k -v > response
# token=$(jq -r '.access_token' <<< "${output}")
token=$(jq -r '.access_token' response)

# Replace `<ROUTE>` with the route of SCDF-server running in CF.
curl \
	-X \
	POST "http://dataflow-9c27ceda-b077-427f-b5fa-c07f606dc5a8.apps.pcfgcp.jagapps.co/streams/deployments/update/demo" \
    -d '{"updateProperties":{"version.proc1":"'"${THE_VERSION}"'"},"releaseName":"proc1","packageIdentifier":{"packageName":"proc1"}}' \
    -H "Content-Type: application/json" \
		-H "Authorization: bearer "${token} \
    -v
