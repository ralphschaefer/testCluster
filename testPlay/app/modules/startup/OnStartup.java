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
    public void OnStartup(Materializer materializer, ActorSystem system, ApplicationLifecycle appLifecycle) {
        System.out.println("Startup Play Framework");
        // System.out.println("AktorSystem: " + system.name());
        // System.out.println("Materializer: " + materializer.toString() );

        AkkaGlobals.getInstance();
        AkkaGlobals.getInstance().setPlaySystem(system);
        AkkaGlobals.getInstance().setPlayMaterializer(materializer);
        appLifecycle.addStopHook(() -> {
            AkkaGlobals.stopInstance();
            System.out.println("Stop Play Framework");
            return CompletableFuture.completedFuture(null);
        });
    }

}
