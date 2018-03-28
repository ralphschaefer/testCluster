package modules.startup;

import javax.inject.*;

import play.inject.ApplicationLifecycle;
import test.emnify.webapp.*;

import java.util.concurrent.CompletableFuture;


@Singleton
public class OnStartup {

    @Inject
    public void OnStartup(ApplicationLifecycle appLifecycle, AkkaGlobals akkaGlobals) {
        System.out.println("Startup Play Framework");

        appLifecycle.addStopHook(() -> {
            akkaGlobals.stop();
            System.out.println("Stop Play Framework");
            return CompletableFuture.completedFuture(null);
        });
    }

}
