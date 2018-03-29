package controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import test.emnify.common.messages.EchoMessage;
import test.emnify.webapp.AbstractGlobals;
import test.emnify.webapp.AkkaGlobalsTest;
import static play.inject.Bindings.bind;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
import static play.test.Helpers.route;
import static play.test.Helpers.*;

public class SendToClusterTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(bind(AbstractGlobals.class).to(AkkaGlobalsTest.class))
                .build();
    }

    @Test
    public void sendToCluster() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        AkkaGlobalsTest.cleanMessages();

        String requestString = "anyString";
        Http.RequestBuilder request = new Http.RequestBuilder()
                .bodyJson(mapper.readTree("{\"message\": \"" + requestString + "\"}"))
                .method(POST)
                .uri("/send");
        Result result = route(app, request);

        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().get());
        String resultMessage = mapper.readTree(contentAsString(result)).get("message").asText();
        assertEquals("copy -> " + requestString,resultMessage);

        Boolean testMessageSend = false;
        for (EchoMessage msg: AkkaGlobalsTest.allMessages)
            if (requestString.equals(msg.getMsg()))
                testMessageSend = true;
        Assert.assertTrue("Message not send",testMessageSend);

    }

}