package test.emnify.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.duration.Duration;
import static akka.testkit.TestKit.shutdownActorSystem;
import akka.testkit.TestKit;
import java.util.concurrent.TimeUnit;
import test.emnify.app.actors.*;
import akka.routing.FromConfig;
import test.emnify.common.actors.Sender;

public class AkkaTestGlobals implements AbstractGlobals {

    private ActorSystem system = null;

    private TestKit testKit = null;

    private ActorRef echoActor = null;

    private ActorRef router = null;

    private ActorRef sender = null;

    public TestKit getTestKit() {
        return testKit;
    }

    public ActorSystem getSystem() {
        return system;
    }

    public ActorRef getEchoActor() {
        return echoActor;
    }

    public ActorRef getRouter() {
        return router;
    }

    public ActorRef getSenderActor() {
        return sender;
    }

    public AkkaTestGlobals() {
        startup();
    }

    public void startup() {
        Config config = ConfigFactory.load("test.conf");
        system = ActorSystem.create("testSystem", config);
        testKit = new TestKit(system);
        echoActor = system.actorOf(Echo.props(this), "echo");
        router = system.actorOf(FromConfig.getInstance().props(), "echoRoute");
        sender = system.actorOf(Sender.props(router), "sender");
    }

    public void shutdown() {

        shutdownActorSystem(system, Duration.create(10, TimeUnit.SECONDS), true);
    }
}
