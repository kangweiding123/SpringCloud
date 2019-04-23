package com.dylan.core.response;

import lombok.*;

import java.io.Serializable;

@Data
@ToString
@Getter
@Setter
public class ResponsResultImpl implements ResponsResultBase, Serializable {
    //操作是否成功
    boolean successed = SUCCESS;

    //操作代码
    String code = SUCCESS_CODE;

    //提示信息
    String message = MSG_SUCCESS;

    /**
     * @return
     * @Description: 使用枚举返回类型构建返回结果
     * @Param [resultCode]
     * @Author chenhaotao
     * @Date 2019/1/5 0005 11:58
     */
    public ResponsResultImpl(ResultCodeEnum resultCodeEnum) {
        this.successed = resultCodeEnum.successed;
        this.code = resultCodeEnum.code;
        this.message = resultCodeEnum.message;
    }

    public ResponsResultImpl() {

    }

    /**
     * @return com.chen.model.web.ResultRespons
     * @Description: 返回成功状态
     * @Param []
     * @Author chenhaotao
     * @Date 2019/1/5 0005 12:03
     */
    public static ResponsResultImpl returnSuccess() {
        return new ResponsResultImpl(ResultCodeEnum.SUCCESS);
    }

    /**
     * @return com.chen.model.web.ResultRespons
     * @Description: 返回失败状态
     * @Param []
     * @Author chenhaotao
     * @Date 2019/1/5 0005 12:03
     */
    public static ResponsResultImpl returnFail() {
        return new ResponsResultImpl(ResultCodeEnum.FAIL);
    }

    /**
     * 返回超时
     *
     * @return
     */
    public static ResponsResultImpl returnTimeOut() {
        return new ResponsResultImpl(ResultCodeEnum.SERVER_ERROR);
    }

    /**
     * 返回参数校验失败
     *
     * @return
     */
    public static ResponsResultImpl returnParamInvalid() {
        return new ResponsResultImpl(ResultCodeEnum.INVALID_PARAM);
    }
}
