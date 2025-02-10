package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.log.LogOperation;
import com.yilena.yilenastudysystem.pojo.Clazz;
import com.yilena.yilenastudysystem.pojo.ClazzQueryParam;
import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result findByPage(ClazzQueryParam param){
        log.info("条件分页查询班级信息");
        return Result.success(clazzService.findByPage(param));
    }

    @LogOperation
    @DeleteMapping("/{id}")
    public Result deleteClazz(@PathVariable String id){
        log.info("删除班级信息");
        clazzService.deleteClazz(id);
        return Result.success();
    }


    @LogOperation
    @PostMapping
    public Result saveClazz(@RequestBody Clazz clazz){
        log.info("保存班级信息");
        clazzService.saveClazz(clazz);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据ID查询班级信息");
        return Result.success(clazzService.findById(id));
    }

    @LogOperation
    @PutMapping
    public Result updateClazz(@RequestBody Clazz clazz){
        log.info("修改班级信息");
        clazzService.updateClazz(clazz);
        return Result.success();
    }

    @GetMapping("/list")
    public Result findAll(){
        log.info("查询班级信息");
        return Result.success(clazzService.findAll());
    }
}
