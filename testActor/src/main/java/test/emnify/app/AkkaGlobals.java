package test.emnify.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import test.emnify.app.actors.*;
import akka.routing.FromConfig;
import test.emnify.common.actors.Sender;

/**
 * Akka artifacts
 */
public class AkkaGlobals implements AbstractGlobals {

    private ActorSystem system =  null;

    private ActorRef echoActor = null;

    private ActorRef router = null;

    private ActorRef senderActor = null;

    public ActorSystem getSystem() {
        return system;
    }

    public ActorRef getEchoActor() {
        return echoActor;
    }

    public ActorRef getSenderActor() {
        return senderActor;
    }

    private AkkaGlobals(boolean isSeed) {
        try {
            System.out.println("Akka Startup");
            system = ActorSystem.create("testSystem");
            echoActor = system.actorOf(Echo.props(this), "echo");
            if (!isSeed) {
                router = system.actorOf(FromConfig.getInstance().props(), "echoRoute");
                senderActor = system.actorOf(Sender.props(router), "sender");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    static private AkkaGlobals instance = null;

    /**
     * factory for instance of AkkaGlobals
     *
     * @param isSeed specifies if node is the seed node
     * @return singelton of AkkaGloblas
     */
    static public AkkaGlobals getInstance(boolean isSeed) {
        if (instance == null)
            instance = new AkkaGlobals(isSeed);
        return instance;
    }

}
