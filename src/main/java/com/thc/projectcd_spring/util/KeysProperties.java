package com.thc.projectcd_spring.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KeysProperties {

    @Value("${email.id}") private String email_id;
    @Value("${email.pw}") private String email_pw;
}
