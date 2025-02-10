package com.yilena.yilenastudysystem.service.impl;

import com.yilena.yilenastudysystem.mapper.OperateLogMapper;
import com.yilena.yilenastudysystem.pojo.OperateLog;
import com.yilena.yilenastudysystem.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public List<OperateLog> findAll() {
        return operateLogMapper.findAll();
    }
}
