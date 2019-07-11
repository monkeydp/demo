package com.monkeydp.demo.thrift.client.event;

import com.monkeydp.demo.thrift.protocol.event.TEvent;
import com.monkeydp.demo.thrift.protocol.event.TEventService;
import lombok.extern.slf4j.Slf4j;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@Slf4j
@ThriftController("/event")
public class EventController implements TEventService.Iface {
    @Override
    public String publish(TEvent event) {
        return EventUtil.receive(event);
    }
}
