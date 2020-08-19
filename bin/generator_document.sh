#!/bin/bash

while getopts 'm:w:' OPT; do
    case $OPT in
        m)
          MODEL_DEFINITION="$OPTARG";;
        w)
          WEBAPI_DEFINITION="$OPTARG";;
        ?)
          echo "Usage: `basename $0` [options] -m model definition localtion , -d webapi definition localtion."
    esac
done

#MODEL_DEFINITION="../src/main/resources/webapi/definition/outbound-service-proxy-model.json"
#WEBAPI_DEFINITION="../src/main/resources/webapi/definition/outbound-service-proxy-service.json"
if [ -z "${MODEL_DEFINITION}" ]; then
  echo "MODEL_DEFINITION is empty!"
  exit 1
fi
if [ -z "${WEBAPI_DEFINITION}" ]; then
  echo "WEBAPI_DEFINITION is empty!"
  exit 1
fi
echo "MODEL_DEFINITION=${MODEL_DEFINITION}"
echo "WEBAPI_DEFINITION=${WEBAPI_DEFINITION}"

cd ..

if [ ! -d "target" ]; then
mvn -U clean package -DskipTests > /dev/null
fi

cd target
TARGET_PATH=`pwd`
java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m doc --model-definition ${MODEL_DEFINITION} --webapi-definition ${WEBAPI_DEFINITION}

cd ./doc/*-doc-tool
mvn test
OUT_PUT_DOC_PATH=`find . -name "outPutDocDesc.txt"`

mv ${OUT_PUT_DOC_PATH} ${TARGET_PATH}