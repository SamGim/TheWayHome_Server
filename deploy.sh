#!/bin/bash

ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no << 'ENDSSH'
@echo off
SET CONTAINER_NAME=thewayhome
SET VERSION=0.0.1
SET SERVER_NAME=wayhome

IF NOT EXIST TheWayHome_Server (
    git clone https://github.com/SamGim/TheWayHome_Server
)

cd TheWayHome_Server
git pull
docker build -t %CONTAINER_NAME%:%VERSION% .

FOR /F "tokens=*" %%i IN ('docker ps -qa -f name=%CONTAINER_NAME%') DO (
    SET ContainerId=%%i
)

IF DEFINED ContainerId (
    FOR /F "tokens=*" %%i IN ('docker ps -q -f name=%CONTAINER_NAME%') DO (
        SET RunningContainerId=%%i
    )

    IF DEFINED RunningContainerId (
        echo "Container is running -> stopping it..."
        docker rm -f wayhome
    )
)

docker run -d --network thewayhome_server_wayhome-net --name wayhome -p 80:8080 -v /c/Users/jykim/TheWayHome_Server/src/main/resources/static/images:/app/src/main/resources/static/images %CONTAINER_NAME%:%VERSION%
ENDSSH

# 원격 서버로부터의 반환 코드 확인
if [ $? -eq 0 ]; then
  echo "Deployment on remote server successful."
else
  echo "Deployment on remote server failed."
fi
