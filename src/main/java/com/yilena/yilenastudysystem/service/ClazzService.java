package com.yilena.yilenastudysystem.service;

import com.yilena.yilenastudysystem.pojo.Clazz;
import com.yilena.yilenastudysystem.pojo.ClazzQueryParam;
import com.yilena.yilenastudysystem.pojo.PageResult;

import java.util.List;

public interface ClazzService {

    PageResult<Clazz> findByPage(ClazzQueryParam param);

    void deleteClazz(String id);

    void saveClazz(Clazz clazz);

    Clazz findById(Integer id);

    void updateClazz(Clazz clazz);

    List<Clazz> findAll();
}
