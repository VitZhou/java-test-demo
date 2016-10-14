package com.test.demo.server.business;


import com.test.demo.commons.EncryptionUtils;
import com.test.demo.server.resource.DemoResource;
import com.test.demo.server.resource.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * mockito and PowerMock example
 */
@PrepareForTest({EncryptionUtils.class})
@RunWith(PowerMockRunner.class)
public class DemoBusinessTest {
    @InjectMocks
    private DemoBusiness demoBusiness;

    @Mock
    private DemoResource demoResource;

    @Before
    public void setUp() throws Exception {
        //每次都重新new一个对象防止出现测试对象有状态
        demoBusiness = new DemoBusiness();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        reset(demoResource);
    }

    @Test
    public void test_login() throws Exception {
        String userName = "userName";
        String password = "password";

        String pwd = password + "md5";
        PowerMockito.mockStatic(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.md5(password)).thenReturn(pwd);

        User user = new User();
        user.setName(userName);
        user.setPassword(pwd);
        when(demoResource.findByName(userName)).thenReturn(Optional.of(user));

        boolean login = demoBusiness.login(userName, password);

        assertThat(login).as("判断是否登录成功").isTrue();
        verify(demoResource).findByName(userName);
    }

    @Test
    public void test_login_fail() throws Exception {
        String userName = "userName";
        String password = "password";

        String pwd = password + "md6";
        PowerMockito.mockStatic(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.md5(password)).thenReturn(pwd);

        User user = new User();
        user.setName(userName);
        user.setPassword(password + "md5");
        when(demoResource.findByName(userName)).thenReturn(Optional.of(user));

        boolean login = demoBusiness.login(userName, password);

        assertThat(login).as("判断是否登录成功").isFalse();
        verify(demoResource).findByName(userName);
    }

    @Test
    public void test_login_fail_user_password_empty() throws Exception {
        String userName = "userName";
        String password = "password";

        String pwd = password + "md5";
        PowerMockito.mockStatic(EncryptionUtils.class);
        PowerMockito.when(EncryptionUtils.md5(password)).thenReturn(pwd);

        User user = new User();
        user.setName(userName);
        when(demoResource.findByName(userName)).thenReturn(Optional.of(user));

        boolean login = demoBusiness.login(userName, password);

        assertThat(login).as("判断是否登录成功").isFalse();
        verify(demoResource).findByName(userName);
    }

    @Test
    public void test_login_fail_user_not_exist() throws Exception {
        String userName = "userName";
        String password = "password";

        when(demoResource.findByName(userName)).thenReturn(Optional.empty());
        boolean login = demoBusiness.login(userName, password);

        assertThat(login).as("判断是否登录成功").isFalse();
        verify(demoResource).findByName(userName);
    }
}