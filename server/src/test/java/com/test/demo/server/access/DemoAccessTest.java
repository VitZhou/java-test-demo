package com.test.demo.server.access;

import com.test.demo.server.business.DemoBusiness;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.test.demo.commons.DemoConstant.LOGIN_FAIL;
import static com.test.demo.commons.DemoConstant.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DemoAccessTest {
    @InjectMocks
    private DemoAccess demoAccess;

    @Mock
    private DemoBusiness demoBusiness;

    @Before
    public void setUp() throws Exception {
        demoAccess = new DemoAccess();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        reset(demoBusiness);
    }

    @Test
    public void test_login() throws Exception {
        String userName = "userName";
        String password = "password";

        when(demoBusiness.login(userName, password)).thenReturn(true);
        String login = demoAccess.login(userName, password);

        assertThat(login).isEqualTo(SUCCESS);
        verify(demoBusiness).login(userName, password);
    }


    @Test
    public void test_login_fail() throws Exception {
        String userName = "userName";
        String password = "password";

        when(demoBusiness.login(userName, password)).thenReturn(false);
        String login = demoAccess.login(userName, password);

        assertThat(login).isEqualTo(LOGIN_FAIL);
        verify(demoBusiness).login(userName, password);
    }

    @Test
    public void test_login_fail_Argument_userName() throws Exception {
        String userName = "";
        String password = "password";

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> demoAccess.login(userName, password))
                .withMessage("%s!", "userName should be not empty")
                .withMessageContaining("userName")
                .withNoCause();
    }

    @Test
    public void test_login_fail_Argument_password() throws Exception {
        String userName = "userName";
        String password = "";

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> demoAccess.login(userName,
                password))
                .withMessage("%s", "password should be not empty")
                .withMessageContaining("password")
                .withNoCause();
    }
}