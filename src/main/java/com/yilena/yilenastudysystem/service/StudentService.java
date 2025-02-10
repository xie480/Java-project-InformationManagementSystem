package com.yilena.yilenastudysystem.service;

import com.yilena.yilenastudysystem.pojo.PageResult;
import com.yilena.yilenastudysystem.pojo.Student;
import com.yilena.yilenastudysystem.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

public interface StudentService {

    PageResult<Student> selectPage(StudentQueryParam param);

    void deleteStudent(List<Integer> ids);

    void saveStudent(Student student);

    Student findById(Integer id);

    void updateStudent(Student student);

    void updateViolation(Integer id, Integer score);

    List<Map<Object,Object>> findDegree();

    List<Map<Object,Object>> findCount();
}
