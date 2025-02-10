package com.yilena.yilenastudysystem.service;

import com.yilena.yilenastudysystem.pojo.Emp;
import com.yilena.yilenastudysystem.pojo.EmpQueryParam;
import com.yilena.yilenastudysystem.pojo.LoginInfo;
import com.yilena.yilenastudysystem.pojo.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface EmpService {
    PageResult<Emp> findByPage(EmpQueryParam param);

    List<Emp> findAll();

    Emp findById(Integer id);

    void deleteEmp(List<Integer> ids);

    void saveEmp(Emp emp);

    void updateEmp(Emp emp);

    List<Map<Object,Object>> findGender();

    List<Map<Object,Object>> findJob();

    LoginInfo login(Emp emp);
}
