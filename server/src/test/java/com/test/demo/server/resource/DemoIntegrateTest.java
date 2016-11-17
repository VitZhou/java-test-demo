package com.test.demo.server.resource;

import com.test.demo.server.ApplicationTest;
import com.test.demo.server.resource.entity.User;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class DemoIntegrateTest {
    private static ConfigurableApplicationContext applicationContext;
    private static DemoResource resource;

    @BeforeClass
    public static void setUp() throws Exception {
        applicationContext = SpringApplication.run(ApplicationTest.class);
        resource = applicationContext.getBean(DemoResource.class);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        applicationContext.stop();
        applicationContext.close();
    }

    @Test
    public void test_findByName() throws Exception {
        Optional<User> jedi = resource.findByName("jedi");
        //assertj
        assertThat(jedi.get()).isNotNull();
        assertThat(jedi.isPresent()).isTrue();
    }

    @Test
    public void test_findByName_not_exist() throws Exception {
        Optional<User> jedi = resource.findByName("jedi_not_exist");
        //assertj
        assertThat(jedi.isPresent()).isFalse();
    }
}