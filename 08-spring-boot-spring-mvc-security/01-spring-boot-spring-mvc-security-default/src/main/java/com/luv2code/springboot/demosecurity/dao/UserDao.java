package com.luv2code.springboot.demosecurity.dao;

import com.luv2code.springboot.demosecurity.entity.User;

public interface UserDao {
    User findByName(String userName);
    void save(User theUser);
}
