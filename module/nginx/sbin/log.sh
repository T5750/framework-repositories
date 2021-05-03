#!/bin/sh

BASE_DIR=/usr/local/nginx
BASE_FILE_NAME=bm.com.access.log

CURRENT_PATH=$BASE_DIR/logs
BAK_PATH=$BASE_DIR/datalogs

CURRENT_FILE=$CURRENT_PATH/$BASE_FILE_NAME
BAK_TIME=`/bin/date -d yesterday +%Y%m%d%H%M`
BAK_FILE=$BAK_PATH/$BAK_TIME-$BASE_FILE_NAME
echo $BAK_FILE

$BASE_DIR/sbin/nginx -s stop

mv $CURRENT_FILE $BAK_FILE

$BASE_DIR/sbin/nginx


