package com.test.demo.server.integrate;

import com.test.demo.server.MybatisConfiguration;
import com.test.demo.server.access.DemoAccess;
import com.test.demo.server.business.DemoBusiness;
import com.test.demo.server.resource.impl.DemoResourceImpl;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.test.demo.commons.DemoConstant.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ContextConfiguration(classes = {DemoAccess.class, MybatisConfiguration.class, DemoBusiness.class,
        DemoResourceImpl.class})
@TestPropertySource(value = "classpath:application.properties")
public class DemoMVCTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    private DemoAccess demoAccess;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(demoAccess).build();
    }

    @Test
    public void test_login() throws Exception {
        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/demo/login.json")
                .param("userName", "jedi").param("password", "abc")
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(contentAsString).isEqualTo(SUCCESS);
    }

}