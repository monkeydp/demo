package com.monkeydp.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author iPotato
 * @date 2019/7/10
 */
@Component
public class UserRegisterListener {
    @EventListener
    public void register(UserRegisterEvent event) {
        User user = event.getUser();
        System.out.println(String.format("@EventListener 注册信息 - 账号: %s, 密码: %s", user.getAccount(), user.getPassword()));
    }
}
