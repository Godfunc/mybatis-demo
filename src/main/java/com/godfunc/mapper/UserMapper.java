package com.godfunc.mapper;

import com.godfunc.entity.User;

import java.util.List;

public interface UserMapper {

    User selectUser(Long id);
    List<User> selectList();
}
