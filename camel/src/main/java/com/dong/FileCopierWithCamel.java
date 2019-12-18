package com.dong;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author DONGSHILEI
 * @create 2019-12-16 20:44
 * @since 1.0
 */
public class FileCopierWithCamel {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {

            @Override
            public void configure() throws Exception {
                from("file:inbox?noop=true").to("file:outbox");
            }
        });

        context.start();

        // 通用没有具体业务意义的代码，只是为了保证主线程不退出
        synchronized (FileCopierWithCamel.class) {
            FileCopierWithCamel.class.wait();
        }

    }
}
