package actors;

import akka.actor.*;
import test.emnify.common.messages.EchoMessage;
import test.emnify.webapp.AkkaGlobals;

public class WebSocketActor extends AbstractActor {

    public static ActorRef currentout = null;

    public static Props props(ActorRef out) {
        WebSocketActor.currentout = out;
        return Props.create(WebSocketActor.class, out);
    }

    private final ActorRef out;

    public WebSocketActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
          .match(String.class, message -> { // <- todo delete
                      out.tell(message, self());
                      AkkaGlobals.getInstance().getSenderActor().tell(new EchoMessage(message), self());
                  }
          )
          .match(EchoMessage.class, echo ->
              out.tell(echo.getMsg(), self())
          )
          .build();
    }
}
