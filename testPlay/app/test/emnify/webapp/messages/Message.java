package test.emnify.webapp.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.Json;
import test.emnify.common.messages.EchoMessage;

import java.io.IOException;

public class Message{

    public String message = null;

    private Message(String message) {
        this.message = message;
    }

    public Message copy() {
        return new Message("copy -> " +this.message);
    }

    public MessageWithSource withSource(String from) {
        return new MessageWithSource(this.message,from);
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

    public static Message apply(String str) throws MessageException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Message.apply(mapper.readTree(str));
        }
        catch (IOException e) {
            throw new MessageException(e.getMessage());
        }
    }

    public static Message build(String message) {
        return new Message(message);
    }

    public static class MessageException extends Exception {
        public MessageException() {
            super("Deserialations failed : " + Message.class);
        }
        public MessageException(String msg) {
            super("generic exception : " + msg);
        }
    }

}