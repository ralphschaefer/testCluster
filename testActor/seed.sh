#!/bin/bash 
set -e
. settings
#NODEIP="127.0.0.1"
#SEED="akka.tcp://testSystem@127.0.0.1:2551"
mvn exec:java -Dexec.mainClass="test.emnify.app.akkatest" -DSEEDNODE=$SEED -Dexec.args="seed" -Dakka.remote.netty.tcp.hostname=$NODEIP
