package com.monkeydp.demo.protobuf.server;

import com.monkeydp.demo.protobuf.protocol.PersonOuterClass.Person;
import org.junit.Assert;
import org.junit.Test;

import static com.monkeydp.demo.protobuf.server.MockFactory.mockPerson;

/**
 * @author iPotato
 * @date 2019/7/14
 */
public class PersonControllerTest extends BaseControllerTest {

    @Test
    public void getTest() throws Exception {
        byte[] bytes = mvcGetAndReturnByteArray("get");
        Person person = Person.parseFrom(bytes);
        Assert.assertEquals(mockPerson(), person);
    }

    @Test
    public void updateAndGetTest() throws Exception {
        Person person = Person.newBuilder()
                              .mergeFrom(mockPerson())
                              .setAge(37)
                              .build();
        byte[] bytes = mvcPutAndReturnByteArray("update-and-get", person);
        Person updatedPerson = Person.parseFrom(bytes);
        Assert.assertEquals(person, updatedPerson);
    }
}
