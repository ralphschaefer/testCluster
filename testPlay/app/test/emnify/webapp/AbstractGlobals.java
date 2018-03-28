package test.emnify.webapp;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

/**
 * interface for Akka artifacts, used for DI
 */
public interface AbstractGlobals {

    ActorSystem getSystem();
    ActorRef getSenderActor();

}

