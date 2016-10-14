package com.test.demo.commons;

import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 模拟加密，为了之后的PowerMock案例的使用
 */
public class EncryptionUtils {
    public static String md5(String str) {
        checkArgument(!Strings.isNullOrEmpty(str), "str should be not empty");
        return str + "md5";
    }
}
