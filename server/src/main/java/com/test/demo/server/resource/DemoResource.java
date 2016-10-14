package com.test.demo.server.resource;


import com.test.demo.server.resource.entity.User;

import java.util.Optional;

public interface DemoResource {
    Optional<User> findByName(String name);
}
