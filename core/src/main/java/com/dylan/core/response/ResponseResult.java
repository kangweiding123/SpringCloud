package com.dylan.core.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Getter
@Setter
public class ResponseResult<T> extends ResponsResultImpl implements Serializable {
    public List<T> result;
    public long total = 0;

    /**
     * 自定义错误返回时
     * @param resultCodeEnum
     * @param result
     * @param total
     */
    public ResponseResult(ResultCodeEnum resultCodeEnum, List<T> result, long total) {
        super(resultCodeEnum);
        this.result = result;
        this.total = total;
    }

    public ResponseResult(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum);
    }

    public ResponseResult(List<T> result, long total) {
        super();
        this.result = result;
        this.total = total;
    }

    public ResponseResult() {
        super();
    }
}
