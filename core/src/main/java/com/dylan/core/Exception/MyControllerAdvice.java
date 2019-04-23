package com.dylan.core.Exception;

import com.dylan.core.response.ResponsResultImpl;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class MyControllerAdvice {
    /**
     * 全局异常捕捉处理
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponsResultImpl errorHandler(Exception ex) {
        return ResponsResultImpl.returnFail();
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResponsResultImpl myErrorHandler(MyException ex) {
        ResponsResultImpl responsResult = new ResponsResultImpl();
        responsResult.setCode(ex.getCode());
        responsResult.setSuccessed(false);
        responsResult.setMessage(ex.getMessage());
        return responsResult;
    }
}
