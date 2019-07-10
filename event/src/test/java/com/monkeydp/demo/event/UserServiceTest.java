package com.monkeydp.demo.event;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author iPotato
 * @date 2019/7/10
 */
public class UserServiceTest extends EventApplicationTest {

    @Autowired
    private UserService userService;

    private User mockUser() {
        return User.builder()
                   .account("root")
                   .password("123456")
                   .build();
    }

    @Test
    public void registerTest() {
        userService.register(mockUser());
    }
}
