package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.pojo.Emp;
import com.yilena.yilenastudysystem.pojo.LoginInfo;
import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping
    public Result login(Emp emp){
        log.info("用户名：{}，密码：{}",emp.getUsername(),emp.getPassword());
        LoginInfo login = empService.login(emp);
        if(login == null)return Result.error("用户名或密码错误");
        return Result.success(login);
    }
}
