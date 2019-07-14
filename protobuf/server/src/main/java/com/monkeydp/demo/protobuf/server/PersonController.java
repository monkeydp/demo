package com.monkeydp.demo.protobuf.server;

import com.monkeydp.demo.protobuf.protocol.PersonOuterClass.Person;
import org.springframework.web.bind.annotation.*;

/**
 * @author iPotato
 * @date 2019/7/14
 */
@RestController
@RequestMapping(value = "person", produces = MediaType.APPLICATION_PROTOBUF_VALUE)
public class PersonController {

    private Person person = MockFactory.mockPerson();

    @GetMapping(value = "get")
    public Person get() {
        return this.person;
    }

    @PutMapping(value = "update")
    public void update(@RequestBody Person person) {
        this.person = person;
    }

    @PutMapping(value = "update-and-get")
    public Person updateAndGet(@RequestBody Person person) {
        this.update(person);
        return this.get();
    }
}
