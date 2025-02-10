package com.yilena.yilenastudysystem.mapper;

import com.yilena.yilenastudysystem.pojo.Clazz;
import com.yilena.yilenastudysystem.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClazzMapper {

    List<Clazz> pageList(ClazzQueryParam param);

    void deleteClazz(String id);

    void saveClazz(Clazz clazz);

    Clazz findById(Integer id);

    void updateClazz(Clazz clazz);

    List<Clazz> findAll();
}
