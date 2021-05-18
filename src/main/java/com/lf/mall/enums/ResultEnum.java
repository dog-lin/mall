package com.lf.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    STOCK_ERROR(1,"库存不足");

    private Integer code;
    private String msg;
}
