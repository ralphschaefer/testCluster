package actors;

import akka.actor.*;
import test.emnify.webapp.AkkaGlobals;
import test.emnify.webapp.messages.Message;

import java.util.ArrayList;

public class WebSocketActor extends AbstractActor {

    public static ArrayList<ActorRef> allWSs = new ArrayList<ActorRef>();


    public static Props props(ActorRef out) {
        WebSocketActor.allWSs.add(out);
        return Props.create(WebSocketActor.class, out);
    }

    private final ActorRef out;

    public WebSocketActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
          .match(String.class, message -> {
                      Message m = Message.apply(message);
                      out.tell(m.withSource("Local").apply(), self());
                      AkkaGlobals.getInstance().getSenderActor().tell(m.toAkkaMessage(), self());
                  }
          )
          .build();
    }
}
