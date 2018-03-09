package kaizone.songmaya.moo.base.util;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

public class SecurityUtilsTest {
    @Test
    public void encryptDES() throws Exception {

        String text = "http://10.164.17.113:8080/";
        String encrypt_des_test = SecurityUtils.encryptDES(text);
        System.out.println(encrypt_des_test);
        Assert.assertEquals(encrypt_des_test, "BqGkxLktTc01wcJXqoNJKedUE+E/6mFC4ELeH1Aexx0=\n");

        String text2 = "http://120.77.219.244:8080";
        String encrypt_des_test2 = SecurityUtils.encryptDES(text2);
        System.out.println(encrypt_des_test2);
    }

    @Test
    public void decryptDES() throws Exception {
        String text = "BqGkxLktTc01wcJXqoNJKedUE+E/6mFC4ELeH1Aexx0=\n";
        System.out.println(SecurityUtils.decryptDES(text));
    }

}