package com.monkeydp.demo.thrift.client;

import com.monkeydp.demo.thrift.protocol.TEvent;

/**
 * @author iPotato
 * @date 2019/7/11
 */
public class EventUtil {

    public static String receive(TEvent event) {
        return String.format("收到事件: %s", event);
    }
}
