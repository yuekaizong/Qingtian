package kaizone.songmaya.moo.base.util;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import kaizone.songmaya.moo.base.config.RSAConfig;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

public class SecurityUtilsTest {
    @Test
    public void encryptDES() throws Exception {

        String text = "http://10.164.17.113:8081/";
        String encrypt_des_test = SecurityUtils.encryptDES(text);
        System.out.println(encrypt_des_test);
        Assert.assertEquals(encrypt_des_test, "BqGkxLktTc01wcJXqoNJKedUE+E/6mFC3RcH0POTOuY=\n");

        String text2 = "http://120.77.219.244:8081/";
        String encrypt_des_test2 = SecurityUtils.encryptDES(text2);
        System.out.println(encrypt_des_test2);
        Assert.assertEquals(encrypt_des_test2, "BqGkxLktTc37E9RRykgTWLJRejPJrXHJQZ8C/WPtC9w=\n");

        String text3 = "Q596760835Q";
        String encrypt_des_test3 = SecurityUtils.encryptDES(text3);
        System.out.println(encrypt_des_test3);
        Assert.assertEquals(encrypt_des_test3, "m/3MnQNskkl9S0c/sbchYg==\n");

        String text4 = "amoo";
        String encrypt_des_test4 = SecurityUtils.encryptDES(text4);
        System.out.println(encrypt_des_test4);
        Assert.assertEquals(encrypt_des_test4, "32u6mu0vmUs=\n");

        String text5 = "1521215999000";
        String encrypt_des_test5 = SecurityUtils.encryptDES(text5);
        System.out.println(encrypt_des_test5);
        Assert.assertEquals(encrypt_des_test5, "EgMdW7s7zu1y30JX1Nj4sQ==\n");

        String text6 = "1560000000000";
        String encrypt_des_test6 = SecurityUtils.encryptDES(text6);
        System.out.println(encrypt_des_test6);
    }

    @Test
    public void decryptDES() throws Exception {
        String text = "BqGkxLktTc01wcJXqoNJKedUE+E/6mFC4ELeH1Aexx0=\n";
        System.out.println(SecurityUtils.decryptDES(text));

        String text2 = "M+ooLbFpNXXK1jPL19gP+w==\n";
        System.out.println(SecurityUtils.decryptDES(text2));
    }

    public enum channel{
        hanying,
        aicai
    }

    @Test
    public void ras() throws Exception {
        PublicKey publicKey2 = RSAUtils.loadPublicKey(RSAConfig.publicKeyStr);
        PrivateKey privateKey2 = RSAUtils.loadPrivateKey(RSAConfig.privateKeyStr);
        // 加密
        String data = "{\"extBSChannel\":\"sdk-test\",\"extUid\":\"qwert\",\"mobile\":\"13167066861\",\"clientVersion\":\"1.0.0\",\"timestamp\":\"2018-03-16 16:02:00\"}";
        System.out.println("原数据data： " + data);
        String encryptStr = Base64.encodeBase64String(RSAUtils.encrypt(data.getBytes(), publicKey2));
        System.out.println("encryptStr : " + encryptStr);

        // 解密
        System.out.println("decodeStr : " + new String(RSAUtils.decrypt(Base64.decodeBase64(encryptStr), privateKey2)));

        System.out.println(channel.aicai);
    }

}