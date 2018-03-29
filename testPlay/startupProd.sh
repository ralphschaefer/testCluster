#!/bin/bash

target/universal/testplay-0.1/bin/testplay -DSEEDNODE=akka.tcp://testSystem@127.0.0.1:2551 -Dplay.http.secret.key="secret"
