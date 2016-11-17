package com.test.demo.server.access;


import com.google.common.base.Strings;

import com.test.demo.server.business.DemoBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.google.common.base.Preconditions.checkArgument;
import static com.test.demo.commons.DemoConstant.LOGIN_FAIL;
import static com.test.demo.commons.DemoConstant.SUCCESS;

@Controller
@EnableAutoConfiguration
@RequestMapping("/demo")
public class DemoAccess {
    @Autowired
    private DemoBusiness demoBusiness;

    @RequestMapping("/login.json")
    @ResponseBody
    public String login(@RequestParam String userName, @RequestParam String password) {
        checkArgument(!Strings.isNullOrEmpty(userName), "userName should be not empty!");
        checkArgument(!Strings.isNullOrEmpty(password), "password should be not empty");
        if (demoBusiness.login(userName, password)) {
            return SUCCESS;
        }
        return LOGIN_FAIL;
    }
}
