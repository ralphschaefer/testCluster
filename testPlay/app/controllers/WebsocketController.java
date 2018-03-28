package controllers;

import actors.WebSocketActor;
import akka.stream.javadsl.Flow;
import com.fasterxml.jackson.databind.JsonNode;
import play.http.websocket.Message;
import play.libs.Json;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import javax.inject.Inject;

public class WebsocketController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final Flow<String,String, ?> actor;
    private Boolean hasSubscription = false;

    @Inject
    public WebsocketController(ActorSystem actorSystem, Materializer materializer) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.actor = ActorFlow.actorRef(WebSocketActor::props, actorSystem, materializer);
    }

    public WebSocket socket() {
        WebSocket ws =  WebSocket.Text.accept(request -> actor);
        this.hasSubscription = true;
        return ws;
    }

    private static class SubscriptionResult {
        final public Boolean hasSubscription;
        private SubscriptionResult(Boolean hasSubscription) {
            this.hasSubscription = hasSubscription;
        }
        protected static JsonNode apply(Boolean hasSubscription) {
            return Json.toJson(new SubscriptionResult(hasSubscription));
        }
    }

    public Result activeSubscription() {
        return ok(SubscriptionResult.apply(this.hasSubscription));
    }

}
