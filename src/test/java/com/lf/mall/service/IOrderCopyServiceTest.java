package com.lf.mall.service;

import com.lf.mall.entity.Order_copy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IOrderCopyServiceTest {
    @Autowired
    private IOrderService service;
    @Test
    void test(){
        Order_copy orderCopy = new Order_copy();
        orderCopy.setId(1);
        orderCopy.setLoginName("lf");
        orderCopy.setUserId(27);
        orderCopy.setUserAddress("gzhu");
        service.save(orderCopy);
    }
}