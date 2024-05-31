package com.scut626.wenjuan_king.exception;


import com.scut626.wenjuan_king.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result ex(SQLIntegrityConstraintViolationException e)
    {
        e.printStackTrace();
        return Result.error("username not available");
    }
}
