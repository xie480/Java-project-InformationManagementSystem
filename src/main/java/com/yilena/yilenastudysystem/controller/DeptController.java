package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.log.LogOperation;
import com.yilena.yilenastudysystem.pojo.Dept;
import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result findAll(){
        log.info("查询所有部门信息");
        return Result.success(deptService.findAll());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("查询部门id:{}",id);
        return Result.success(deptService.findById(id));
    }

    @LogOperation
    @DeleteMapping
    public Result deleteDept(Integer id){
        log.info("删除部门id:{}",id);
        deptService.deleteDept(id);
        return Result.success();
    }

    @LogOperation
    @PostMapping
    public Result saveDept(@RequestBody Dept dept){
        log.info("新增部门信息:{}",dept);
        deptService.saveDept(dept);
        return Result.success();
    }

    @LogOperation
    @PutMapping
    public Result updateDept(@RequestBody Dept dept){
        log.info("修改部门信息:{}",dept);
        deptService.updateDept(dept);
        return Result.success();
    }
}
