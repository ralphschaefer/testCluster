#!/bin/bash 
set -e
export NODEIP="127.0.0.1"
export SEED="akka.tcp://testSystem@127.0.0.1:2551"
java -DSEEDNODE=$SEED -Dakka.remote.netty.tcp.port=0 -Dakka.remote.netty.tcp.hostname=$NODEIP -jar target/scala-2.12/testActor.jar

