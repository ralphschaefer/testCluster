#!/bin/bash 
set -e
NODEIP="127.0.0.1"
SEED="akka.tcp://testSystem@127.0.0.1:2551"
java -DSEEDNODE=$SEED -Dexec.args="seed" -Dakka.remote.netty.tcp.hostname=$NODEIP -jar target/scala-2.12/testActor.jar seed

