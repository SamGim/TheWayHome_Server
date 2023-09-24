@echo off
set local
set CONTAINER_NAME=thewayhome-server
set VERSION=0.0.1

if not exist "TheWayHome_Server" (
    git clone https://github.com/SamGim/TheWayHome_Server
)

cd TheWayHome_Server
git pull
docker build -t %CONTAINER_NAME%:%VERSION% .

docker ps -qa -f name=%CONTAINER_NAME% >nul 2>&1
if errorlevel 1 (
    echo Container is not exist or not running.
) else (
    docker ps -q -f name=%CONTAINER_NAME% >nul 2>&1
    if errorlevel 1 (
        echo Container exists but is not running.
    ) else (
        echo Container is running -> stopping it…
        docker-compose down
    )
)

docker-compose up -d