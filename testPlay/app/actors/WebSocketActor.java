package actors;

import akka.actor.*;
import test.emnify.common.messages.EchoMessage;

public class WebSocketActor extends AbstractActor {

    public static Props props(ActorRef out) {
        return Props.create(WebSocketActor.class, out);
    }

    private final ActorRef out;

    public WebSocketActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
          .match(String.class, message ->    // <- todo delete
              out.tell("I received your message: " + message, self())
          )
          .match(EchoMessage.class, echo ->
            out.tell(echo.getMsg(), self())
          )
          .build();
    }
}
