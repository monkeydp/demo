package com.monkeydp.demo.thrift.client.event;

import com.monkeydp.demo.thrift.protocol.event.TEvent;

/**
 * @author iPotato
 * @date 2019/7/11
 */
public class EventUtil {

    public static String receive(TEvent event) {
        return String.format("收到事件: %s", event);
    }
}
