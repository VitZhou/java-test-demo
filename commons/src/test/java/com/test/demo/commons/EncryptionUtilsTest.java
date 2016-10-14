package com.test.demo.commons;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;


public class EncryptionUtilsTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testMd5() throws Exception {
        String str = "str";
        String md5 = EncryptionUtils.md5(str);
        assertThat(md5).isEqualTo(str+"md5");
    }

    @Test
    public void test_md5_err(){
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("str should be not empty");
        EncryptionUtils.md5("");
    }
}