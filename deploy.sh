#!/bin/bash

ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no << 'ENDSSH'
@echo off
SET CONTAINER_NAME=thewayhome
SET VERSION=0.0.1

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
        docker-compose down --rmi local
    )
)

docker-compose up -d
ENDSSH

# 원격 서버로부터의 반환 코드 확인
if [ $? -eq 0 ]; then
  echo "Deployment on remote server successful."
else
  echo "Deployment on remote server failed."
fi
