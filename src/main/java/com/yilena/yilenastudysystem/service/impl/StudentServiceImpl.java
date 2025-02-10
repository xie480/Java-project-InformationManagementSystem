package com.yilena.yilenastudysystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yilena.yilenastudysystem.mapper.StudentMapper;
import com.yilena.yilenastudysystem.pojo.PageResult;
import com.yilena.yilenastudysystem.pojo.Student;
import com.yilena.yilenastudysystem.pojo.StudentQueryParam;
import com.yilena.yilenastudysystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> selectPage(StudentQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Student> list = studentMapper.pageList(param);
        Page<Student> p = (Page<Student>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteStudent(List<Integer> ids) {
        studentMapper.deleteStudent(ids);
    }

    @Override
    public void saveStudent(Student student) {
        studentMapper.saveStudent(student);
    }

    @Override
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentMapper.updateStudent(student);
    }

    @Override
    public void updateViolation(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }

    @Override
    public List<Map<Object, Object>> findDegree() {
        return studentMapper.findDegree();
    }

    @Override
    public List<Map<Object, Object>> findCount() {
        return studentMapper.findCount();
    }
}
