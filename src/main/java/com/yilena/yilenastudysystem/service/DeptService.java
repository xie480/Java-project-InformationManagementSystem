package com.yilena.yilenastudysystem.service;

import com.yilena.yilenastudysystem.pojo.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    void deleteDept(Integer id);

    void saveDept(Dept dept);

    void updateDept(Dept dept);

    Dept findById(Integer id);
}
