package com.monkeydp.demo.thrift.server.event;

import com.monkeydp.demo.thrift.protocol.event.TEvent;
import com.monkeydp.demo.thrift.protocol.event.TEventService;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@Service
public class EventService {

    @ThriftClient
    TEventService.Client client;

    @SneakyThrows
    public String getPublishing(TEvent event) {
        return client.publish(event);
    }
}
