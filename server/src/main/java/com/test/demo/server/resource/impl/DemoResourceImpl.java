package com.test.demo.server.resource.impl;


import com.test.demo.server.resource.DemoResource;
import com.test.demo.server.resource.entity.User;
import com.test.demo.server.resource.mapper.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class DemoResourceImpl implements DemoResource {
    private static Logger logger = LoggerFactory.getLogger(DemoResourceImpl.class);
    @Autowired
    private UserMapper userMapper;

    public Optional<User> findByName(String name) {
        try {
            User user = userMapper.findByName(name);
            if (user == null) {
                return Optional.empty();
            }
            return Optional.of(user);
        } catch (Exception e) {
            logger.error("error to find user, userName={}", name, e);
            return Optional.empty();
        }
    }
}
