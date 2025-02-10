package com.yilena.yilenastudysystem.mapper;

import com.yilena.yilenastudysystem.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Select("select id, name, create_time as createTime, update_time as updateTime  from dept")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteDept(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    void saveDept(Dept dept);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void updateDept(Dept dept);

    @Select("select id, name, create_time as createTime, update_time as updateTime  from dept where id = #{id}")
    Dept findById(Integer id);

    @Select("select count(*) from emp where dept_id = #{id}")
    int findEmpInDept(Integer id);
}
