package controllers;

import actors.WebSocketActor;
import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.libs.streams.ActorFlow;
import play.mvc.*;
import akka.actor.*;
import akka.stream.*;
import test.emnify.webapp.AbstractGlobals;

import javax.inject.Inject;

public class WebsocketController extends Controller {

    private final ActorSystem actorSystem;
    private final Materializer materializer;
    private final AbstractGlobals clusterAkkaArtifacts;
    private Boolean hasSubscription = false;

    @Inject
    public WebsocketController(ActorSystem actorSystem, Materializer materializer, AbstractGlobals globals ) {
        this.actorSystem = actorSystem;
        this.materializer = materializer;
        this.clusterAkkaArtifacts = globals;
    }

    public WebSocket socket() {
        WebSocket ws =  WebSocket.Text.accept(request ->
                ActorFlow.actorRef(o -> {
                        return WebSocketActor.props(o,this.clusterAkkaArtifacts);
                    }, actorSystem, materializer)
        );
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
