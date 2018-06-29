#!/usr/bin/env sh

set -e -u

cd scdf-zone/processor-sample

# build the project
./mvnw package -DskipTests

# move the jar to a dedicated output directory
cp target/processor-sample-*.jar ../../output/

# find the project version
THE_VERSION=$(grep -o '<version[^"]*' pom.xml | sed -e 's/<version>\(.*\)<\/version>/\1/')

# move the extracted version also to the dedicated output directory
echo "THE_VERSION is -> $THE_VERSION"
echo $THE_VERSION > ../../output/projectVersion
