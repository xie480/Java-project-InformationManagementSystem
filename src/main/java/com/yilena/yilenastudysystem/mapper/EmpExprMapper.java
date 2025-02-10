package com.yilena.yilenastudysystem.mapper;

import com.yilena.yilenastudysystem.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    void saveEmpExpr(List<EmpExpr> exprList);

    void deleteEmpExprByEmpId(List<Integer> ids);

    List<EmpExpr> findById(Integer id);
}
