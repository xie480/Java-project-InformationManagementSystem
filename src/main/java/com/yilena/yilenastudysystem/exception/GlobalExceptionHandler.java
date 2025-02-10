package com.yilena.yilenastudysystem.exception;

import com.yilena.yilenastudysystem.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handleException(Exception e) {
        log.error("******服务器发生异常******   ", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler
    public Result handleException(MyCustomException e) {
        log.error("******服务器发生异常******   ", e);
        return Result.error(e.getMessage());
    }
}
