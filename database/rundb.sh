#!/bin/env bash

SCRIPT_DIR=$( cd -- "$(dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd)

if ! [[ ${MYSQL_ROOT_PASSWORD} ]]; then
    MYSQL_ROOT_PASSWORD=Debug123
fi

if ! [ -e ${SCRIPT_DIR}/init.sql ]; then 
    echo init.sql not found
    exit 1
fi

if [[ $(docker ps -a | grep mysqlc) ]]; then
    docker rm mysqlc --force
fi

docker run -d \
    -p 127.0.0.1:3306:3306 \
    -v ${SCRIPT_DIR}/data:/var/lib/mysql \
    -v ${SCRIPT_DIR}/init.sql:/docker-entrypoint-initdb.d/init.sql \
    -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
    --name mysqlc \
    mysql:8.0


