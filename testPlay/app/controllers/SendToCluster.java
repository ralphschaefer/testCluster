package controllers;

import akka.actor.ActorRef;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.*;
import test.emnify.common.messages.EchoMessage;
import test.emnify.webapp.AkkaGlobals;

public class SendToCluster extends Controller {

    public static class Message{

        public String message = null;
        private Message(String message) {
            this.message = message;
        }
        public Message copy() {
            return new Message("copy -> " +this.message);
        }
        public JsonNode apply() {
            return Json.toJson(this);
        }
        public EchoMessage toAkkaMessage() {
            return new EchoMessage(this.message);
        }

        public static Message apply(JsonNode json) throws MessageException {
            String message = json.findPath("message").textValue();
            if (message == null)
                throw new MessageException();
            return new Message(message);
        }

        public static class MessageException extends Exception {
            public MessageException() {
                super("Deserialations faild :" + Message.class);
            }
        }

    }

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
