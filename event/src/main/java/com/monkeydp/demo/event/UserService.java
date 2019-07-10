package com.monkeydp.demo.event;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author iPotato
 * @date 2019/7/10
 */
@Service
public class UserService {

    @NotNull
    private final ApplicationContext applicationContext;

    @Autowired
    public UserService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void register(User user) {
        applicationContext.publishEvent(new UserRegisterEvent(this, user));
    }
}
