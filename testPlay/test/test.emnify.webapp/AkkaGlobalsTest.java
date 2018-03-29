package test.emnify.webapp;

import akka.actor.ActorRef;
import test.emnify.common.messages.EchoMessage;

import javax.inject.*;
import java.util.ArrayList;

@Singleton
public class AkkaGlobalsTest implements AbstractGlobals {

    public static ArrayList<EchoMessage> allMessages = new ArrayList<>();

    public static void cleanMessages() {
        allMessages = new ArrayList<>();
    }

    public void sendMesssage(EchoMessage msg, ActorRef sender) {
        AkkaGlobalsTest.allMessages.add(msg);
    }

    public void stop() {

    }

    public AkkaGlobalsTest() {
        System.out.println("AkkaGlobalsTest Module");
    }

}
