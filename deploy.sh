#!/bin/bash

start=$(date +"%s")

# 원격 서버에 전달할 스크립트
REMOTE_SCRIPT='
CONTAINER_NAME=thewayhome
VERSION=0.0.1

# 원격 서버 작업 디렉토리로 이동
cd ~/your/working/directory

if [ ! -d TheWayHome_Server ]; then
  git clone https://github.com/SamGim/TheWayHome_Server
fi

cd TheWayHome_Server
git pull
docker build -t $CONTAINER_NAME:$VERSION .

if [ "$(docker ps -qa -f name=$CONTAINER_NAME)" ]; then
    if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
        echo "Container is running -> stopping it..."
        docker-compose down
    fi
fi

docker-compose up -d
'
# 원격 서버로 SSH 연결하여 스크립트 실행
ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no "${REMOTE_SCRIPT}"
#ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no << 'ENDSSH'

#ENDSSH
# 원격 서버로부터의 반환 코드 확인
# shellcheck disable=SC2181
if [ $? -eq 0 ]; then
  echo "Deployment on remote server successful."
else
  echo "Deployment on remote server failed."
fi
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
