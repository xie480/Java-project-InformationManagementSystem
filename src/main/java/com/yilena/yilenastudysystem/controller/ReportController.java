package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.service.EmpService;
import com.yilena.yilenastudysystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private EmpService empService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/empGenderData")
    public Result ReportEmpGender(){
        log.info("统计员工所有性别");
        return Result.success(empService.findGender());
    }

    @GetMapping("/empJobData")
    public Result ReportEmpJob(){
        log.info("统计员工所有职位");
        return Result.success(empService.findJob());
    }

    @GetMapping("/studentDegreeData")
    public Result ReportStudentDegree(){
        log.info("统计学生所有学位");
        return Result.success(studentService.findDegree());
    }

    @GetMapping("/studentCountData")
    public Result ReportStudentCount(){
        log.info("统计学生数量");
        return Result.success(studentService.findCount());
    }
}
