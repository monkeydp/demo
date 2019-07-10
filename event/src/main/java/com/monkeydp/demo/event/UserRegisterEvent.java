package com.monkeydp.demo.event;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEvent;

/**
 * @author iPotato
 * @date 2019/7/10
 */
@Getter
public class UserRegisterEvent extends ApplicationEvent {

    private static final long serialVersionUID = 7899368757606053895L;

    @NotNull
    private User user;

    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
