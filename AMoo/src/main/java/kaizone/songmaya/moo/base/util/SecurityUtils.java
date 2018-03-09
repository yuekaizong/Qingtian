package kaizone.songmaya.moo.base.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {

    // 初始化向量，随意填充
    private static byte[] iv = "biaoge89".getBytes();
    public final static String ENCRYPT_KEY = "1234qwer";

    // DES加密
    // encryptText为原文
    // encryptKey为密匙
    public static String encryptDES(String encryptText)
            throws Exception {
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        IvParameterSpec spec = new IvParameterSpec(iv);
        // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
        SecretKeySpec key = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密码初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        // 执行加密操作
        byte[] encryptData = cipher.doFinal(encryptText.getBytes());
        // 返回加密后的数据
        return Base64.encodeToString(encryptData, Base64.DEFAULT);
    }

    // 解密
    public static String decryptDES(String decryptString)
            throws Exception {
        // 先使用Base64解密
        byte[] base64byte = Base64.decode(decryptString, Base64.DEFAULT);
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        IvParameterSpec spec = new IvParameterSpec(iv);
        // 实例化SecretKeySpec类,根据字节数组来构造SecretKeySpec
        SecretKeySpec key = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "DES");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密码初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        // 获取解密后的数据
        byte decryptedData[] = cipher.doFinal(base64byte);
        // 将解密后数据转换为字符串输出
        return new String(decryptedData);
    }
}
