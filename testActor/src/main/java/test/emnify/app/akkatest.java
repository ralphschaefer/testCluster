package test.emnify.app;

import akka.actor.ActorRef;
import akka.actor.CoordinatedShutdown;
import test.emnify.app.actors.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import test.emnify.common.messages.EchoMessage;

/**
 * main class for akka clustertest
 */
class akkatest {

  /**
   * starting point for akka clustertest
   *
   * if the comandline argument "seed" is passed here, the
   * created node will act as seed node, otherwise as worker node
   *
   * @param args
   */
  public static void main(String[] args) {


    boolean seed = false;
    for (String item : args) {
      if (item.equals("seed"))
        seed = true;
    }

    AbstractGlobals akkaGlobals = AkkaGlobals.getInstance(seed);

    try {
      if (seed)
      {
        System.out.println("any key to exit");
        System.in.read();
      }
      else {
        BufferedReader reader =
                   new BufferedReader(new InputStreamReader(System.in));
        String input = "N/A";
        System.out.println("enter something or 'exit'");
        while (!input.equals("exit")) {
          input = reader.readLine();
          akkaGlobals.getSenderActor().tell(
                new EchoMessage(input),
                ActorRef.noSender());
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
      CoordinatedShutdown.get(akkaGlobals.getSystem()).run();
    } finally {
      CoordinatedShutdown.get(akkaGlobals.getSystem()).run();
      System.out.println("Term");
    }

  }

}


