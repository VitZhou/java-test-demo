package com.test.demo.server.integrate;

import com.test.demo.commons.DemoConstant;
import com.test.demo.server.Application;
import com.test.demo.server.ApplicationTest;
import com.test.demo.server.access.DemoAccess;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static com.test.demo.commons.DemoConstant.LOGIN_FAIL;
import static com.test.demo.commons.DemoConstant.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;

public class DemoResource {
    private static ConfigurableApplicationContext applicationContext;
    private static DemoAccess demoAccess;
    @BeforeClass
    public static void setUp() throws Exception {
        applicationContext = SpringApplication.run(ApplicationTest.class);
        demoAccess = applicationContext.getBean(DemoAccess.class);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        applicationContext.stop();
        applicationContext.close();
    }

    @Test
    public void test_login(){
        String login = demoAccess.login("jedi", "abc");
        assertThat(login).isEqualTo(SUCCESS);
    }

    @Test
    public void test_login_fail(){
        String login = demoAccess.login("jedi", "abcd");
        assertThat(login).isEqualTo(LOGIN_FAIL);
    }
}
