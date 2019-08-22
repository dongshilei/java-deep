package com.dong.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author DONGSHILEI
 * @create 2019-04-08 17:10
 */
public class HelloWorld  extends UntypedActor {

    private ActorRef greeter;

    /**
     * 在Actor启动前会完成初始化工作
     * 创建Greeter实例，并向它发送GREET消息
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception {
        greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
        System.out.println("Greeter Actor Path:"+ greeter.path());
        greeter.tell(Greeter.Msg.GREET,getSelf());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Greeter.Msg.DONE) {
            greeter.tell(Greeter.Msg.GREET,getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }
}
