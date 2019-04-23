package com.dylan.core.Exception;

import lombok.Getter;

@Getter
public class MyException extends RuntimeException {
    private String code;
    private String msg;

    public MyException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
