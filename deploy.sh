#!/bin/bash

start=$(date +"%s")

ssh -p ${SERVER_PORT} ${SERVER_USER}@${SERVER_HOST} -i key.txt -o StrictHostKeyChecking=no << 'ENDSSH'
wsl zsh -c '
CONTAINER_NAME=thewayhome
VERSION=0.0.1

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
exit
ENDSSH

if [ $? -eq 0 ]; then
  exit 0
else
  exit 1
fi

end=$(date +"%s")

diff=$(($end - $start))

echo "Deployed in : ${diff}s"
