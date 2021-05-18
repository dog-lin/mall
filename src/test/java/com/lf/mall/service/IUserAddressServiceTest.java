package com.lf.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class IUserAddressServiceTest {
    @Autowired
    private IUserAddressService service;
    @Test
    void test(){
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",10);
        service.listByMap(map).forEach(System.out::println);

    }
}