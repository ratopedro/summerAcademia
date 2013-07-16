#!/bin/bash

### Configuration ###
#
WARFILE="imws.war"
WEBAPPS="/opt/jetty/webapps/"
HOSTNAME="atnog-funnet"
PORT="22"
USERNAME="root"

START=$(date +%s)

echo "Deploying $WARFILE"
echo

echo "Compile $WARFILE"
ant -f build.xml
echo

echo "Synchronize server $HOSTNAME:$WEBAPPS"
rsync -av --progress --inplace --rsh="ssh -p$PORT" dist/$WARFILE \
${USERNAME}@${HOSTNAME}:${WEBAPPS}
echo

echo "Restart $WARFILE"
ssh $USERNAME@$HOSTNAME "exec service rc.jetty restart&"
echo

STOP=$(date +%s)
TIME=$((STOP-START))
echo "Done ($TIME seconds)."
exit 0
