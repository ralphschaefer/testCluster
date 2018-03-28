package controllers;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;
import test.emnify.webapp.AkkaGlobals;
import test.emnify.webapp.messages.Message;

public class SendToCluster extends Controller {

    public Result handleSendRequest() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return badRequest("no valid Json");
            Message msg = Message.apply(json);
            AkkaGlobals.getInstance().getSenderActor().tell(msg.toAkkaMessage(), ActorRef.noSender());
            return ok(msg.copy().apply());
        } catch (Message.MessageException e) {
            return badRequest(e.getMessage());
        }
    }

}
