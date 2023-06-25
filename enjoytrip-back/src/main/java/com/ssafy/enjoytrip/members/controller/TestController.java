package com.ssafy.enjoytrip.members.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TestController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/test")
    public String test(){
        return "haha";
    }
    @GetMapping("/passencode")
    public String encodeTest(@RequestParam(value = "pass") String pass){
        return bCryptPasswordEncoder.encode(pass);
    }

}
