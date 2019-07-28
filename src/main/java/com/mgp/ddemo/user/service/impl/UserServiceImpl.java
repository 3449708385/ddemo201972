package com.mgp.ddemo.user.service.impl;

import com.mgp.ddemo.user.bean.User;
import com.mgp.ddemo.user.dao.UserMapper;
import com.mgp.ddemo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired(required = false)@Qualifier("userMapper")
    private UserMapper userMapper;

    @Override
    public List<User> queryUserList() {
        int i = 10/0;
        return userMapper.queryByAll();
    }

    @Override
    public List<User> queryUserListByUserName(String username) {
        return userMapper.queryByUserName(username);
    }
}
