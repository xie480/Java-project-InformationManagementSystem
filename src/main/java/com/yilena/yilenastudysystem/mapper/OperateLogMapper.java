package com.yilena.yilenastudysystem.mapper;

import com.yilena.yilenastudysystem.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {
    @Select("select id, operate_emp_id as operateEmpId, operate_time as operateTime, class_name as className, method_name as methodName, method_params as methodParams, return_value as returnValue, cost_time as costTime from operate_log")
    List<OperateLog> findAll();

   @Insert("insert into operate_log(operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
           "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime})")
    void saveOperateLog(OperateLog operateLog);
}
