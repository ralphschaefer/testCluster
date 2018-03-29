package controllers;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;
import test.emnify.webapp.AbstractGlobals;
import test.emnify.webapp.messages.Message;
import javax.inject.Inject;

public class SendToCluster extends Controller {

    final AbstractGlobals akkaGlobals;

    @Inject
    public SendToCluster(AbstractGlobals akkaGlobals) {
        this.akkaGlobals = akkaGlobals;
    }


    public Result handleSendRequest() {
        try {
            JsonNode json = request().body().asJson();
            if (json == null)
                return badRequest("no valid Json");
            Message msg = Message.apply(json);
            akkaGlobals.sendMesssage(msg.toAkkaMessage(), ActorRef.noSender());
            return ok(msg.copy().apply());
        } catch (Message.MessageException e) {
            return badRequest(e.getMessage());
        }
    }

}
