package com.monkeydp.demo.thrift.server.greeting;

import com.monkeydp.demo.thrift.protocol.greeting.TGreetingService;
import com.monkeydp.demo.thrift.protocol.greeting.TName;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

/**
 * @author iPotato
 * @date 2019/7/11
 */
@ThriftController("/greeting")
public class GreetingController implements TGreetingService.Iface {
    @Override
    public String greet(TName name) {
        return GreetingUtil.hello(name);
    }
}
