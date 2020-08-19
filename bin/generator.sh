#!/bin/bash
cd ..
PROJECT_PATH=`pwd`
PROJECT_TARGET_PATH=`pwd`"/target/"

# 根据输入参数，获取文件名称；获取mvn要执行的命令，mvn命令缺省值为install
while getopts 'n:c:' OPT; do
    case $OPT in
        n)
          DEFINITION_NAME="$OPTARG";;
        c)
          MVN_COMMAND="$OPTARG";;
        ?)
          echo "Usage: `basename $0` [options]"
          echo "Options:"
          echo "-n    definition_name, like outbound-service-proxy."
          echo "-c    mvn command, default install."
    esac
done
if [ -z "${DEFINITION_NAME}" ]; then
  echo "definition_name is empty! use Options -n definition_name, like outbound-service-proxy."
  exit 1
fi
MVN_COMMAND=${MVN_COMMAND:-"install"}

echo "definition_name="${DEFINITION_NAME}


# 编译项目
cd ${PROJECT_PATH}
echo "Waiting for packaged code-generator project."
mvn -U clean package -DskipTests > /dev/null
if [ ! -d "${PROJECT_TARGET_PATH}" ]; then
  echo "\"mvn -U clean package -DskipTests\" execution failed. "
  exit 1
fi
echo "Packaged code-generator project done."
echo ""

IFS=","
arr=($DEFINITION_NAME)
for ((i=0;i<${#arr[@]};i++))
do
 DEFINITION_NAME=${arr[$i]}
 MODEL_FILE=${PROJECT_PATH}"/src/main/resources/webapi/definition/"${DEFINITION_NAME}"-model.json"
 SERVICE_FILE=${PROJECT_PATH}"/src/main/resources/webapi/definition/"${DEFINITION_NAME}"-service.json"
 DB_FILE=${PROJECT_PATH}"/src/main/resources/db/definition/"${DEFINITION_NAME}".json"

 cd ${PROJECT_TARGET_PATH}
 # 生成*-api-model包
 if [ ! -f "${MODEL_FILE}" ]; then
   echo 'model definition file='${MODEL_FILE}' does not exist！'
   echo 'Skip the generation of '${DEFINITION_NAME}'-api-model ！'
 else
   cd ${PROJECT_TARGET_PATH}
   echo "java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m model -d ${MODEL_FILE}"
   java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m model -d ${MODEL_FILE}
   cd webapi/${DEFINITION_NAME}-api-model
   echo "cd ${PROJECT_TARGET_PATH}webapi/${DEFINITION_NAME}-api-model"
   echo "mvn -U clean ${MVN_COMMAND} "
   mvn -U clean ${MVN_COMMAND} 1>/dev/null
   echo "ll ./target/*jar "
   echo `ls -l ./target/*jar`
   echo ""
 fi

 # 生成*-api-service包
 # 生成*--feign-sdk包
 if [ ! -f "${SERVICE_FILE}" ]; then
   echo 'service definition file='${SERVICE_FILE}' does not exist！'
   echo 'Skip the generation of '${DEFINITION_NAME}'-api-service！'
   echo 'Skip the generation of '${DEFINITION_NAME}'-feign-sdk !'
 else
   cd ${PROJECT_TARGET_PATH}
   echo "java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m webapi -d ${SERVICE_FILE}"
   java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m webapi -d ${SERVICE_FILE}
   cd webapi/${DEFINITION_NAME}-api-service
   echo "cd ${PROJECT_TARGET_PATH}webapi/${DEFINITION_NAME}-api-service"
   echo "mvn -U clean ${MVN_COMMAND} "
   mvn -U clean ${MVN_COMMAND} 1>/dev/null
   echo "ll ./target/*jar "
   echo `ls -l ./target/*jar`
   echo ""

   cd ${PROJECT_TARGET_PATH}
   echo "java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m feign -d ${SERVICE_FILE}"
   java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m feign -d ${SERVICE_FILE}
   cd webapi/${DEFINITION_NAME}-feign-sdk
   echo "cd ${PROJECT_TARGET_PATH}webapi/${DEFINITION_NAME}-feign-sdk"
   echo "mvn -U clean ${MVN_COMMAND} "
   mvn -U clean ${MVN_COMMAND} 1>/dev/null
   echo "ll ./target/*jar "
   echo `ls -l ./target/*jar`
   echo ""
 fi


 # 生成-db-api-mybatis包
 if [ ! -f "${DB_FILE}" ]; then
   echo 'db definition file='${DB_FILE}' does not exist！'
   echo 'Skip the generation of '${DB_FILE}'-db-api-mybatis！'
 else
   cd ${PROJECT_TARGET_PATH}
   echo "java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m db -d ${DB_FILE}"
   java -jar code-generator-1.0.0-SNAPSHOT-spring-boot.jar -m db -d ${DB_FILE}
   cd db/${DEFINITION_NAME}-db-api-mybatis
   echo "cd ${PROJECT_TARGET_PATH}db/${DEFINITION_NAME}-db-api-mybatis"
   echo "mvn -U clean ${MVN_COMMAND} "
   mvn -U clean ${MVN_COMMAND} 1>/dev/null
   echo "ll ./target/*jar "
   echo `ls -l ./target/*jar`
   cd ${PROJECT_TARGET_PATH}
 fi

 cd ${PROJECT_PATH}/bin


 # 生成静态文档
 if [ ! -f "${MODEL_FILE}" ]; then
   echo 'model definition file='${MODEL_FILE}' does not exist！'
   echo 'Skip the generation of document！'
   exit 1
 fi
 if [ ! -f "${SERVICE_FILE}" ]; then
   echo 'service definition file='${SERVICE_FILE}' does not exist！'
   echo 'Skip the generation of document！'
   exit 1
 fi
 echo "----------------------------------------------------"
 echo "sh generator_document.sh -m ${MODEL_FILE} -w ${SERVICE_FILE} 2>&1 >/dev/null"
 sh generator_document.sh -m ${MODEL_FILE} -w ${SERVICE_FILE} 2>&1 >/dev/null
 cd ${PROJECT_PATH}
 echo ""
 echo "generator document ..."
 echo "ll ./target/outPutDocDesc.txt"
 echo `ls -l ./target/outPutDocDesc.txt`


done
