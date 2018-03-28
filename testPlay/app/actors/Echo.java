package actors;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.ClusterEvent.MemberUp;
import play.libs.streams.ActorFlow;
import test.emnify.common.messages.EchoMessage;
import test.emnify.webapp.AkkaGlobals;

public class Echo extends AbstractActor {

    Cluster cluster = Cluster.get(getContext().getSystem());

    @Override
    public void preStart() {
        cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(),
            MemberEvent.class, UnreachableMember.class);
    }

    @Override
    public void postStop() {
        cluster.unsubscribe(getSelf());
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder().
                match(MemberUp.class, mUp -> {
                })
                .match(EchoMessage.class, e-> {
                    System.out.println("(" + getSender().toString() + ") -> " + e.getMsg());
                    if (WebSocketActor.currentout != null)
                        WebSocketActor.currentout.tell("(" + getSender().toString() + ") -> " + e.getMsg(),self());
                }).build();

    }

    /**
     * akka property factory
     *
     * @return property for Echo actor
     */
    static public Props props() {
        return Props.create(
                Echo.class,
                () -> new Echo()
        );
    }

}
