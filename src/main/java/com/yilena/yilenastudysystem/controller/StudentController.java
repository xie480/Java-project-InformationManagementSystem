package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.log.LogOperation;
import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.pojo.Student;
import com.yilena.yilenastudysystem.pojo.StudentQueryParam;
import com.yilena.yilenastudysystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result findByPage(StudentQueryParam param){
        log.info("条件分页查询学生信息");
        return Result.success(studentService.selectPage(param));
    }
    @LogOperation
    @DeleteMapping("/{ids}")
    public Result deleteStudent(@PathVariable List<Integer> ids){
        log.info("删除学生信息");
        studentService.deleteStudent(ids);
        return Result.success();
    }

    @LogOperation
    @PostMapping
    public Result saveStudent(@RequestBody Student student){
        log.info("保存学生信息");
        studentService.saveStudent(student);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据ID查询学生信息");
        return Result.success(studentService.findById(id));
    }

    @LogOperation
    @PutMapping
    public Result updateStudent(@RequestBody Student student){
        log.info("更新学生信息");
        studentService.updateStudent(student);
        return Result.success();
    }

    @LogOperation
    @PutMapping("/violation/{id}/{score}")
    public Result updateViolation(@PathVariable Integer id, @PathVariable Integer score){
        log.info("更新学生违纪信息");
        studentService.updateViolation(id, score);
        return Result.success();
    }
}
