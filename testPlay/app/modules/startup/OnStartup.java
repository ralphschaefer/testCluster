package modules.startup;

import javax.inject.*;

import akka.actor.ActorSystem;
import akka.stream.Materializer;
import play.api.libs.concurrent.Akka;
import play.inject.ApplicationLifecycle;
import test.emnify.webapp.*;

import java.util.concurrent.CompletableFuture;


@Singleton
public class OnStartup {

    @Inject
    public void OnStartup(ApplicationLifecycle appLifecycle) {
        System.out.println("Startup Play Framework");

        AkkaGlobals.getInstance();
        appLifecycle.addStopHook(() -> {
            AkkaGlobals.stopInstance();
            System.out.println("Stop Play Framework");
            return CompletableFuture.completedFuture(null);
        });
    }

}
