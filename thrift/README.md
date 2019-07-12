# Thrift Demo

There are three submodules:
- protocol
- server
- client


## 1. Generate code file

Run task **compileThrift** to generate code file like `.java`, `.js` by `.thrift`

Gradle → thrift-protocol → Tasks → other → compileThrift

---

File `.thrift` in 

`thrift-protocol/src/main/thrift`

Generated code file in **gen-java** 

`thrift-protocol/build/generated-sources/thrift/gen-java`

___

Refresh gradle to recognize **gen-java** as source dir after **compileThrift**

___

> Now, only support to run task **compileThrift** in windows
>
> For other operating system, you have to config thrift executor like `thrift-0.12.0.exe` by yourself


## 2. Rpc calling

### 2.1 Client calls service in Server

All code files related to **greeting** is designed for this

- Start `ThriftServerApplication`
- Run test `GreetingServiceTest.getGreetingTest()`

### 2.2. Server calls service in Client

All code files related to **event** is designed for this

- Start `ThriftClientApplication`
- Run test `EventServiceTest.getPublishingTest()`

### 2.3 Write your own service

Use **greeting** as an example

1.In thrift-server
 
Write a `GreetingController`

```java
package com.monkeydp.demo.thrift.server.greeting;

import com.monkeydp.demo.thrift.protocol.greeting.TGreetingService;
import com.monkeydp.demo.thrift.protocol.greeting.TName;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

@ThriftController("/greeting")
public class GreetingController implements TGreetingService.Iface {
    @Override
    public String greet(TName name) {
        return GreetingUtil.hello(name);
    }
}
```

2.In thrift-client

Write a  `GreetingService`

```java
package com.monkeydp.demo.thrift.client.greeting;

import com.monkeydp.demo.thrift.protocol.greeting.TGreetingService;
import com.monkeydp.demo.thrift.protocol.greeting.TName;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    @ThriftClient
    TGreetingService.Client client;

    @SneakyThrows
    public String getGreeting(TName name) {
        return client.greet(name);
    }
}
```

Config service in **application.yml**

```yaml
app.thrift-server.url: http://localhost:8080/
tGreetingService:                             # service name
  endpoint: ${app.thrift-server.url}greeting  # direct endpoint
  ribbon:                                     # manually ribbon
    listOfServers: ${app.thrift-server.url}
  path: /service                              # general path
  connectTimeout: 1000                        # default=1000
  readTimeout: 10000                          # default=30000
```