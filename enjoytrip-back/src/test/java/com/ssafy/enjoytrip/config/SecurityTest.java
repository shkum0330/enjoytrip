package com.ssafy.enjoytrip.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class SecurityTest {

   PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Test
    void encodeTest(){

        String pass1 = encoder.encode("1234");
        String pass2 = encoder.encode("1234");

        System.out.println("pass1 = " + pass1);
        System.out.println("pass2 = " + pass2);
        assertThat(pass1).isEqualTo(pass2);
    }
}