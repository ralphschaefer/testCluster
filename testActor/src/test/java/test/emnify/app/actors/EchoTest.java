package test.emnify.app.actors;

import org.junit.*;

import test.emnify.app.AkkaTestGlobals;
import test.emnify.common.messages.EchoMessage;

public class EchoTest {

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
    public void testEchoActor() {
        globals.getEchoActor().tell(new EchoMessage("test"),globals.getTestKit().testActor());
        globals.getTestKit().expectNoMsg();
    }

}

