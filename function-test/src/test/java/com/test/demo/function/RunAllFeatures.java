package com.test.demo.function;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * format:指定cucumber生成feature文档的目录与类型
 * features:指定.feature文件存放的目录
 * 当maven执行mvn install命令时会通过junit4找到features指定的目录执行所有的.feature文件
 */
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, format = {"json:target/cucumber-report/cucumber.json",
        "html:target/cucumber-report"},
        features = "classpath:feature")
public class RunAllFeatures {

}
