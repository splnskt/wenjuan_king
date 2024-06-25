package com.scut626.wenjuan_king.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {

    public static Result success(Object o)
    {
        return new Result(0, "ok", o);
    }
    public static Result success()
    {
        return success(0);
    }
    public static Result error(String msg)
    {
        return new Result(1, msg, null);
    }
    public static Result nologin()
    {
        return new Result(-1, "no login", null);
    }

    private Integer code;
    private String msg;
    private Object data;
}
