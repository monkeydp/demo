package com.monkeydp.demo.thrift.server;

import com.monkeydp.demo.thrift.protocol.TGreetingService;
import com.monkeydp.demo.thrift.protocol.TName;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@ThriftController("/api")
public class GreetingController implements TGreetingService.Iface {
    @Override
    public String greet(TName name) {
        return String.format(
                "Hello, %s %s!",
                name.getFirstName(),
                name.getSecondName()
        );
    }
}
