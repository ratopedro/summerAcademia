#!/bin/bash

### Configuration ###
#
SERVICE="cdb"
REMOTE_PATH="/opt/$SERVICE/"
HOSTNAME="atnog-funnet"
PORT="22"
USERNAME="root"

START=$(date +%s)

echo "Deploying $SERVICE"
echo

echo "Compile $SERVICE"
ant -f build.xml
echo

echo "Synchronize server $HOSTNAME:$REMOTE_PATH"
rsync -av --progress --inplace --rsh="ssh -p$PORT" dist/$SERVICE.jar $SERVICE \
${USERNAME}@${HOSTNAME}:${REMOTE_PATH}
echo

echo "Restart $SERVICE"
ssh -p $PORT -l$USERNAME $HOSTNAME "exec insserv $REMOTE_PATH/$SERVICE"
ssh -p $PORT -l$USERNAME $HOSTNAME  "exec service $SERVICE restart"
echo

STOP=$(date +%s)
TIME=$((STOP-START))
echo "Done ($TIME seconds)."
exit 0
