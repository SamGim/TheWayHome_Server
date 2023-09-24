##!/bin/bash
#
#start=$(date +"%s")
#
#ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no << 'ENDSSH'
#wsl zsh -c "
#CONTAINER_NAME=thewayhome-server
#VERSION=0.0.1
#
#if [ ! -d TheWayHome_Server ]; then
#  git clone https://github.com/SamGim/TheWayHome_Server
#fi
#
#cd TheWayHome_Server
#git pull
#docker build -t $CONTAINER_NAME:$VERSION .
#
#if [ "$(docker ps -qa -f name=$CONTAINER_NAME)" ]; then
#    if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
#        echo "Container is running -> stopping it..."
#        docker-compose down
#    fi
#fi
#
#docker-compose up -d
#"
#exit
#ENDSSH
#
#if [ $? -eq 0 ]; then
#  exit 0
#else
#  exit 1
#fi
#
#end=$(date +"%s")
#
#diff=$(($end - $start))
#
#echo "Deployed in : ${diff}s"
#


#!/bin/bash

# 로컬 스크립트 파일 경로
LOCAL_SCRIPT="./deploy.bat"

# 스크립트 파일을 원격 서버로 복사
scp -P ${SERVER_PORT} -i key.txt ${LOCAL_SCRIPT} ${SERVER_USER}@${SERVER_HOST}:~/

# 원격 서버에서 스크립트 실행
ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no "./$(basename ${LOCAL_SCRIPT})"

# 원격 서버로부터의 반환 코드 확인
if [ $? -eq 0 ]; then
  echo "Deployment on remote server successful."
else
  echo "Deployment on remote server failed."
fi
