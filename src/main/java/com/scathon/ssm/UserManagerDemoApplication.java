package com.scathon.ssm;

import com.scathon.ssm.mapper.UserMapper;
import com.scathon.ssm.pojo.Users;
import com.scathon.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UserManagerDemoApplication {
    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public Users get() {
        return userService.findByUsername("linhd");
    }

    public static void main(String[] args) {
        SpringApplication.run(UserManagerDemoApplication.class, args);
    }
}
