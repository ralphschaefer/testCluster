package test.emnify.webapp;


import actors.Echo;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.CoordinatedShutdown;
import akka.routing.FromConfig;
import test.emnify.common.actors.Sender;
import javax.inject.*;
import test.emnify.common.messages.EchoMessage;

/**
 * Akka artifacts
 */
@Singleton
public class AkkaGlobals implements AbstractGlobals {

    private ActorSystem system =  null;

    private ActorRef router = null;

    private ActorRef echoActor = null;

    private ActorRef senderActor = null;

    public void sendMesssage(EchoMessage msg, ActorRef sender) {
        if (senderActor != null)
            senderActor.tell(msg, sender);
    }

    public void stop() {
        System.out.println("Akka shutdown");
        CoordinatedShutdown.get(system).run();
    }

    @Inject
    public AkkaGlobals() {
        try {
            System.out.println("Akka Startup");
            system = ActorSystem.create("testSystem");
            echoActor = system.actorOf(Echo.props(), "echo");
            router = system.actorOf(FromConfig.getInstance().props(), "echoRoute");
            senderActor = system.actorOf(Sender.props(router), "sender");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

}
