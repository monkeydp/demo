package com.monkeydp.demo.thrift.server;

import com.monkeydp.demo.thrift.protocol.TName;

/**
 * @author iPotato
 * @date 2019/7/11
 */
public class GreetingUtil {

    public static String hello(TName name) {
        return String.format(
                "Hello %s %s",
                name.getFirstName(),
                name.getSecondName()
        );
    }
}
