package test.emnify.common.actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

import test.emnify.common.messages.EchoMessage;
/**
 * sender actor
 *
 * send any message of type "Echo.EchoMessage" to the cluster
 */
public class Sender extends AbstractActor {

    private ActorRef router = null;

    /**
     * constructor
     * @param router router for cluster
     */
    public Sender(ActorRef router) {
        this.router = router;
    }

    /**
     * property builder for Sender actor
     *
     * @param router
     * @return
     */
    static public Props props(ActorRef router) {
        return Props.create(
                Sender.class,
                router
        );
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        .match(EchoMessage.class, echo -> router.tell(echo, getSelf()))
        .build();
    }

}
