package test.emnify.webapp;

import akka.actor.ActorRef;
import test.emnify.common.messages.EchoMessage;

public interface AbstractGlobals {

    void sendMesssage(EchoMessage msg, ActorRef sender);

    void stop();
}

