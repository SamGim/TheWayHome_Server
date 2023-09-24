#!/bin/bash

start=$(date +"%s")

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
        docker-compose down
    )
)

docker-compose up -d
ENDSSH

if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi

end=$(date +"%s")

diff=$(($end - $start))

echo "Deployed in : ${diff}s"
