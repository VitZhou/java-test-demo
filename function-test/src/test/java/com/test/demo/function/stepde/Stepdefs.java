package com.test.demo.function.stepde;

import com.google.common.collect.ImmutableMap;

import com.test.demo.commons.DemoConstant;
import com.test.demo.commons.EncryptionUtils;
import com.test.demo.function.Application;
import com.test.demo.function.DbUnitProperties;
import com.test.demo.function.DemoProperties;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;

import static com.test.demo.commons.DemoConstant.LOGIN_FAIL;
import static com.test.demo.commons.DemoConstant.SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
public class Stepdefs implements En {
    @Autowired
    private DbUnitProperties dbUnitProperties;

    @Autowired
    private DemoProperties demoProperties;

    private String userName;
    private String password;
    private String result;

    //dbunit
    private IDatabaseTester databaseTester;
    private static final String TABLE_NAME = "test_user";
    private File file;

    private static final Map<String, String> resultMap = ImmutableMap.of(
            "成功", SUCCESS,
            "失败", LOGIN_FAIL
    );

    //建立链接,备份数据
    @Before
    public void setUp() throws Exception {
        databaseTester = new JdbcDatabaseTester(dbUnitProperties.getClassName(),
                dbUnitProperties.getUrl(), dbUnitProperties.getUserName(), dbUnitProperties.getPassword());

        //数据备份
        QueryDataSet backupDataSet = new QueryDataSet(databaseTester.getConnection());
        backupDataSet.addTable(TABLE_NAME);
        file = File.createTempFile(TABLE_NAME + "_back", ".xml");
        FlatXmlDataSet.write(backupDataSet, new FileOutputStream(file));
    }

    //清楚测试数据,还原备份数据
    @After
    public void tearDown() throws Exception {
        IDatabaseConnection connection = databaseTester.getConnection();
        try {
            databaseTester.onTearDown();

            //数据回滚
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(file);
            DatabaseOperation.CLEAN_INSERT.execute(databaseTester.getConnection(), dataSet);
            //删除临时文件
            file.deleteOnExit();
        } finally {
            connection.close();
        }
    }

    @When("^存在用户名为(\\S+)密码为(\\S+)的用户$")
    public void when(String userName, String password) throws Throwable {
        IDatabaseConnection connection = databaseTester.getConnection();
        try {
            //这里一定要用classLoad去加载文件,否则会出现java的相对路劲和maven相对路劲不一致的问题
            IDataSet dataSet = new FlatXmlDataSetBuilder().build(Stepdefs.class.getResourceAsStream("/dataset/dataset.xml"));
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);
            replacementDataSet.addReplacementObject("${name}", userName);
            replacementDataSet.addReplacementObject("${password}", EncryptionUtils.md5(password));
            DatabaseOperation.CLEAN_INSERT.execute(connection, replacementDataSet);
        } finally {
            connection.close();
        }
    }

    @And("^输入用户名(\\S+)$")
    public void inputUserName(String userName) throws Throwable {
        this.userName = userName;
    }

    @And("^输入密码(\\S+)$")
    public void inputPassword(String password) throws Throwable {
        this.password = password;
    }

    @And("^点击登录按钮$")
    public void login() throws Throwable {
        String url = demoProperties.getUrl() + "?userName=" + userName + "&password=" + password;
        result = readContentFromGet(url);
    }

    @Then("^登录(\\S+)$")
    public void then(String success) {
        String s = resultMap.get(success);
        assertThat(result).isEqualTo(s);
    }

    /**
     * 发送请求，获取服务器返回结果
     *
     * @return 服务器返回结果
     */
    private String readContentFromGet(String getURL) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(getURL);
        HttpResponse response = httpClient.execute(httpGet);
        return EntityUtils.toString(response.getEntity());
    }

}
