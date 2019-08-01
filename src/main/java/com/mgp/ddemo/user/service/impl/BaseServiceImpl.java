package com.mgp.ddemo.user.service.impl;

import com.mgp.ddemo.user.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl implements BaseService {
    @Override
    public void test() {
        System.out.println("调用service服务");
    }
}
