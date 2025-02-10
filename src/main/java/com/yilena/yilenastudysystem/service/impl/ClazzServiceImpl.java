package com.yilena.yilenastudysystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yilena.yilenastudysystem.mapper.ClazzMapper;
import com.yilena.yilenastudysystem.pojo.Clazz;
import com.yilena.yilenastudysystem.pojo.ClazzQueryParam;
import com.yilena.yilenastudysystem.pojo.PageResult;
import com.yilena.yilenastudysystem.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public PageResult<Clazz> findByPage(ClazzQueryParam param) {
        PageHelper.startPage(param.getPage(), param.getPageSize());
        List<Clazz> list = clazzMapper.pageList(param);
        Page<Clazz> p = (Page<Clazz>)list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void deleteClazz(String id) {
        clazzMapper.deleteClazz(id);
    }

    @Override
    public void saveClazz(Clazz clazz) {
        clazzMapper.saveClazz(clazz);
    }

    @Override
    public Clazz findById(Integer id) {
        return clazzMapper.findById(id);
    }

    @Override
    public void updateClazz(Clazz clazz) {
        clazzMapper.updateClazz(clazz);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }
}
