package test.emnify.app.actors;

import akka.actor.ActorRef;
import akka.testkit.javadsl.TestKit;
import org.junit.*;

import test.emnify.app.AkkaTestGlobals;
import test.emnify.common.actors.Sender;
import test.emnify.common.messages.EchoMessage;

public class SenderTest {

    AkkaTestGlobals globals = null;

    @Before
    public void startSystem() {
        globals = new AkkaTestGlobals();
    }

    @After
    public void stopSystem() {
        globals.shutdown();
        globals = null;
    }

    @Test
    public void testSenderActorForProbeDest()
    {
        new TestKit(globals.getSystem()) {{
            ActorRef probe = getTestActor();
            ActorRef sender = getSystem().actorOf(Sender.props(probe), "sender2");
            EchoMessage m = new EchoMessage("MSG");
            sender.tell(m, probe);
            expectMsg(m);
        } };
    }

    @Test
    public void testSenderActor() {
        globals.getSenderActor().tell(new EchoMessage("testSender"), globals.getTestKit().testActor());
        globals.getTestKit().expectNoMsg();
    }

}
