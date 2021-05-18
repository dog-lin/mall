package com.lf.mall.exception;

import com.lf.mall.enums.ResultEnum;

public class MallException extends RuntimeException{
    public MallException(String message) {
        super(message);
    }

    public MallException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }
}
