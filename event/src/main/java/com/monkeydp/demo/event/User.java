package com.monkeydp.demo.event;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author iPotato
 * @date 2019/7/10
 */
@Data
@Builder
public class User {
    @NotNull
    private String account;
    @NotNull
    private String password;
}
