package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import test.emnify.webapp.AbstractGlobals;
import test.emnify.webapp.AkkaGlobalsTest;
import static play.inject.Bindings.bind;
import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import static play.test.Helpers.*;

public class WebsocketControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .overrides(bind(AbstractGlobals.class).to(AkkaGlobalsTest.class))
                .build();
    }

    @Test
    public void queryWSStatus() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/hasListeners");
        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertEquals("application/json", result.contentType().get());
        Boolean subscription = mapper.readTree(contentAsString(result)).get("hasSubscription").asBoolean();

        Assert.assertFalse("ws schoud have no subscriptions", subscription);
    }

}
