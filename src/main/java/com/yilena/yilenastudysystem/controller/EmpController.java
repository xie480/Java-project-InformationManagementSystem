package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.log.LogOperation;
import com.yilena.yilenastudysystem.pojo.Emp;
import com.yilena.yilenastudysystem.pojo.EmpQueryParam;
import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/list")
    public Result findAll() {
        log.info("查询所有员工信息");
        return Result.success(empService.findAll());
    }

    @GetMapping
    public Result findByPage(EmpQueryParam param){
        log.info("分页查询员工信息");
        return Result.success(empService.findByPage(param));
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        log.info("查询员工id:{}", id);
        return Result.success(empService.findById(id));
    }

    @LogOperation
    @DeleteMapping
    public Result deleteEmp(@RequestParam List<Integer> ids) {
        log.info("删除员工，员工id：{}", ids);
        empService.deleteEmp(ids);
        return Result.success();
    }

    @LogOperation
    @PostMapping
    public Result saveEmp(@RequestBody Emp emp) {
        log.info("新增员工，员工信息：{}", emp);
        empService.saveEmp(emp);
        return Result.success();
    }
    @LogOperation
    @PutMapping
    public Result updateEmp(@RequestBody Emp emp) {
        log.info("修改员工，员工信息：{}", emp);
        empService.updateEmp(emp);
        return Result.success();
    }
}
