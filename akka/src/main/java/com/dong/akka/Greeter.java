package com.dong.akka;

import akka.actor.UntypedActor;

/**
 * @author DONGSHILEI
 * @create 2019-04-08 17:13
 */
public class Greeter extends UntypedActor {
    public static enum Msg {
        GREET, DONE;
    }
    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Msg.GREET){
            System.out.println("Hello World@!");
            getSender().tell(Msg.DONE,getSelf());
        } else if (msg == Msg.DONE){
            getContext().stop(getSelf());
            unhandled(msg);
        }
    }
}
