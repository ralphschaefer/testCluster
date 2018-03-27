package test.emnify.webapp;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import akka.routing.FromConfig;
import test.emnify.common.actors.Sender;

/**
 * Akka artifacts
 */
public class AkkaGlobals implements AbstractGlobals {

    private ActorSystem system =  null;

    private ActorRef router = null;

    private ActorRef senderActor = null;

    public ActorSystem getSystem() {
        return system;
    }

    public ActorRef getSenderActor() {
        return senderActor;
    }

    private AkkaGlobals() {
        try {
            System.out.println("Akka Startup");
            system = ActorSystem.create("testSystem");
            router = system.actorOf(FromConfig.getInstance().props(), "echoRoute");
            senderActor = system.actorOf(Sender.props(router), "sender");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    static private AkkaGlobals instance = null;

    static public AkkaGlobals getInstance() {
        if (instance == null)
            instance = new AkkaGlobals();
        return instance;
    }

    static public void stopInstance() {
        if (instance != null) {
            System.out.println("Akka shutdown");
            CoordinatedShutdown.get(instance.getSystem()).run();
            instance = null;
        }
    }
}
