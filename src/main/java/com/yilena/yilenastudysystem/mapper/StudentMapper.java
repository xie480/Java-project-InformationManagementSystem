package com.yilena.yilenastudysystem.mapper;

import com.yilena.yilenastudysystem.pojo.Student;
import com.yilena.yilenastudysystem.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> pageList(StudentQueryParam param);

    void deleteStudent(List<Integer> ids);

    void saveStudent(Student student);

    Student findById(Integer id);

    void updateStudent(Student student);

    void updateViolation(Integer id, Integer score);

    @MapKey("degree")
    List<Map<Object, Object>> findDegree();

    @MapKey("clazzName")
    List<Map<Object, Object>> findCount();
}
