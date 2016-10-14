package com.test.demo.server.resource.mapper;

import com.test.demo.server.resource.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User findByName(String name);
}
