package test.emnify.webapp.messages;

import play.libs.Json;

public class MessageWithSource {
    public String message = null;
    public String source = null;

    public MessageWithSource(String message, String source) {
        this.message = message;
        this.source = source;
    }

    public String apply() {
        return Json.toJson(this).toString();
    }
}
