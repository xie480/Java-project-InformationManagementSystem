package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/page")
    public Result findAll() {
        return Result.success(operateLogService.findAll());
    }
}
