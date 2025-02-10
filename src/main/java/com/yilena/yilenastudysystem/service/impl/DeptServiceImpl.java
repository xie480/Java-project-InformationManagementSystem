package com.yilena.yilenastudysystem.service.impl;

import com.yilena.yilenastudysystem.mapper.DeptMapper;
import com.yilena.yilenastudysystem.pojo.Dept;
import com.yilena.yilenastudysystem.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }

    @Override
    public void deleteDept(Integer id) {
        if(deptMapper.findEmpInDept(id) != 0){
            throw new RuntimeException("该部门有员工，不能删除");
        }
        deptMapper.deleteDept(id);
    }

    @Override
    public void saveDept(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.saveDept(dept);
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.updateDept(dept);
    }

    @Override
    public Dept findById(Integer id) {
        return deptMapper.findById(id);
    }
}
