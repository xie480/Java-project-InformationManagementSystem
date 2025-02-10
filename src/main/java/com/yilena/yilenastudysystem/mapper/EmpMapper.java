package com.yilena.yilenastudysystem.mapper;

import com.yilena.yilenastudysystem.pojo.Emp;
import com.yilena.yilenastudysystem.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    List<Emp> list(EmpQueryParam param);

    Emp findById(Integer id);

    void deleteEmp(List<Integer> ids);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert(" insert into emp(username, password, name, gender, phone, job, salary, image, enter_date, dept_id, create_time, update_time)" +
            "values (#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{enterDate}, #{deptId}, #{createTime}, #{updateTime})")
    void saveEmp(Emp emp);

    void updateEmp(Emp emp);

    @MapKey("gender")
    List<Map<Object, Object>> findGender();

    @MapKey("job")
    List<Map<Object, Object>> findJob();

    @Select("select id,name,username from emp where username = #{username} and password = #{password}")
    Emp findByUsernameAndPassword(Emp emp);
}
