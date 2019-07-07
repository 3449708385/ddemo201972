package com.mgp.ddemo.user.service;

import com.mgp.ddemo.user.bean.User;

import java.util.List;

public interface UserService {

    public List<User> queryUserList();
    public List<User> queryUserListByUserName(String username);
}
