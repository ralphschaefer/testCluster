package test.emnify.common.messages;

import java.io.Serializable;

public class EchoMessage implements Serializable {
    private String msg = null;
    public EchoMessage(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }
}
