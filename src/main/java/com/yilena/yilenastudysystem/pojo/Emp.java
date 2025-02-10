package com.yilena.yilenastudysystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private Integer id;
    private String username;
    private String password = "123456";
    private String name;
    private Integer gender;
    private String phone;
    private String job;
    private Integer salary;
    private String image;
    private LocalDate enterDate;
    private Integer deptId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private String deptName;

    private List<EmpExpr> exprList;

    private String token;
}
