package com.dylan.core.response;

import lombok.ToString;

@ToString
public enum ResultCodeEnum implements ResultCodeBase {

    SUCCESS(true,"0000","操作成功！"),
    UNAUTHENTICATED(false,"10001","此操作需要登陆系统！"),
    UNAUTHORISE(false,"10002","权限不足，无权操作！"),
    INVALID_PARAM(false,"10003","非法参数！"),
    FAIL(false,"11111","操作失败！"),
    SERVER_ERROR(false,"99999","抱歉，系统繁忙，请稍后重试！");

    //操作是否成功
    boolean successed;
    //操作代码
    String code;
    //提示信息
    String message;

    private ResultCodeEnum(boolean successed,String code, String message){
        this.successed = successed;
        this.code = code;
        this.message = message;
    }

    public boolean successed() {
        return successed;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
