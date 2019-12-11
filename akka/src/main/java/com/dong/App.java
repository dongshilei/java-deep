package com.dong;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.dong.akka.HelloWorld;
import com.typesafe.config.ConfigFactory;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Hello");
        ActorRef a = system.actorOf(Props.create(HelloWorld.class), "helloWorld");
        System.out.println("HelloWorld Actor Path:"+a.path());
    }
}
