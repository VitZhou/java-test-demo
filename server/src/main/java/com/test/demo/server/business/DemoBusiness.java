package com.test.demo.server.business;

import com.google.common.base.Strings;

import com.test.demo.commons.EncryptionUtils;
import com.test.demo.server.resource.DemoResource;
import com.test.demo.server.resource.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DemoBusiness {
    @Autowired
    private DemoResource demoResource;

    public boolean login(String userName, String password) {
        Optional<User> optional = demoResource.findByName(userName);
        if (!optional.isPresent()) {
            return false;
        }

        User user = optional.get();
        String userPwd = user.getPassword();
        String md5 = EncryptionUtils.md5(password);
        if (!Strings.isNullOrEmpty(userPwd) && userPwd.equals(md5)) {
            return true;
        }
        return false;
    }
}
