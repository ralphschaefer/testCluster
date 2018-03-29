package actors;

import akka.actor.*;
import test.emnify.webapp.AbstractGlobals;
import test.emnify.webapp.messages.Message;

import java.util.ArrayList;

public class WebSocketActor extends AbstractActor {

    final AbstractGlobals akkaGlobals;

    public static ArrayList<ActorRef> allWSs = new ArrayList<ActorRef>();

    public static Props props(ActorRef out, AbstractGlobals akkaGlobals) {
        WebSocketActor.allWSs.add(out);
        return Props.create(WebSocketActor.class, out, akkaGlobals);
    }

    private final ActorRef out;

    public WebSocketActor(ActorRef out, AbstractGlobals akkaGlobals) {
        this.out = out;
        this.akkaGlobals = akkaGlobals;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
          .match(String.class, message -> {
                      Message m = Message.apply(message);
                      out.tell(m.withSource("Local").apply(), self());
                      akkaGlobals.sendMesssage(m.toAkkaMessage(), self());
                  }
          )
          .build();
    }
}
