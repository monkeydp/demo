package com.monkeydp.demo.protobuf.server;

import com.monkeydp.demo.protobuf.protocol.PersonOuterClass.Person;

/**
 * @author iPotato
 * @date 2019/7/14
 */
public class MockFactory {
    public static Person mockPerson() {
        return Person.newBuilder()
                     .setName("iPotato")
                     .setAge(24)
                     .setUpdatedAt(System.currentTimeMillis())
                     .build();
    }
}
