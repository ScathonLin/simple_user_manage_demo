package com.scathon.ssm.controller;

import com.scathon.ssm.pojo.Users;
import com.scathon.ssm.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhd on 2018/7/11.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/admin/delete/{username}")
    public String delete(@PathVariable("username") String username) {
        userService.deleteUser(username);
        return "redirect:/dev/list";
    }

    @RequestMapping("/admin/audit/{username}")
    public String audit(@PathVariable("username") String username) {
        userService.updateStatus(username);
        return "redirect:/dev/list";
    }

    @RequestMapping("/dev/add")
    public String add(Users users) {
        userService.insertOne(users);
        return "redirect:/dev/list";
    }

    @RequestMapping("/dev/list")
    public String listUser(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "index";
    }

    @RequestMapping("/user/{username}")
    public String user(@PathVariable("username") String username, Model model) {
        Users user = userService.findByUsername(username);
        List<Users> userList = new ArrayList<>();
        userList.add(user);
        model.addAttribute("userList", userList);
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
