package test.emnify.app.actors;

import org.junit.*;

import test.emnify.app.AkkaTestGlobals;

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
        globals.getEchoActor().tell(new Echo.EchoMessage("test"),globals.getTestKit().testActor());
        globals.getTestKit().expectNoMsg();
    }

}

