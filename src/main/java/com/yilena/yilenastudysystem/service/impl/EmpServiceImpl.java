package com.yilena.yilenastudysystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yilena.yilenastudysystem.mapper.EmpExprMapper;
import com.yilena.yilenastudysystem.mapper.EmpMapper;
import com.yilena.yilenastudysystem.pojo.*;
import com.yilena.yilenastudysystem.service.EmpService;
import com.yilena.yilenastudysystem.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public PageResult<Emp> findByPage(EmpQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Emp> empList = empMapper.list(param);
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public List<Emp> findAll() {
        return empMapper.list(null);
    }

    @Override
    public Emp findById(Integer id) {
        Emp emp = empMapper.findById(id);
        emp.setExprList(empExprMapper.findById(id));
        return emp;
    }

    @Transactional
    @Override
    public void deleteEmp(List<Integer> ids) {
        empMapper.deleteEmp(ids);
        empExprMapper.deleteEmpExprByEmpId(ids);
    }

    @Transactional
    @Override
    public void saveEmp(Emp emp) {
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.saveEmp(emp);
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.saveEmpExpr(exprList);
        }
    }

    @Transactional
    @Override
    public void updateEmp(Emp emp) {
        empMapper.updateEmp(emp);
        empExprMapper.deleteEmpExprByEmpId(Collections.singletonList(emp.getId()));
        if (!CollectionUtils.isEmpty(emp.getExprList())) {
            emp.getExprList().forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.saveEmpExpr(emp.getExprList());
        }
    }

    @Override
    public List<Map<Object, Object>> findGender() {
        return empMapper.findGender();
    }

    @Override
    public List<Map<Object, Object>> findJob() {
        return empMapper.findJob();
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp e = empMapper.findByUsernameAndPassword(emp);
        if (e == null) {
            return null;
        }
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("id", e.getId());
        dataMap.put("username", e.getUsername());
        String jwt = JwtUtils.generateToken(dataMap);
        return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
    }
}
