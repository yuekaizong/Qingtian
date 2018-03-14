package kaizone.songmaya.moo.base.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by yuelibiao on 2018/3/14.
 */

public class RSAUtils {

    private static final String TAG = "RSAUtils";

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";

    private static final String ALGORITHM = "RSA";

    public static byte[] encrypt (byte[] data, PublicKey publicKey) {
        try {
            byte[] dataReturn = new byte[0];
            Cipher cipher = Cipher.getInstance (RSA_ECB_PKCS1_PADDING);
            cipher.init (Cipher.ENCRYPT_MODE, publicKey);

            // 加密时超过117字节就报错。为此采用分段加密的办法来加密
            StringBuilder sb = new StringBuilder ();
            for (int i = 0; i < data.length; i += 100) {
                byte[] doFinal = cipher.doFinal (ArrayUtils.subarray (data, i, i + 100));
                sb.append (new String (doFinal));
                dataReturn = ArrayUtils.addAll (dataReturn, doFinal);
            }

            return dataReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 解密时超过128字节就报错。为此采用分段解密的办法来解密
            byte[] dataReturn = new byte[0];
            for (int i = 0; i < encryptedData.length; i += 128) {
                byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(encryptedData, i, i + 128));
                dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
            }
            return dataReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static KeyPair generateRSAKeyPair () {
        return generateRSAKeyPair (1024);
    }

    public static KeyPair generateRSAKeyPair (int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance (ALGORITHM);
            kpg.initialize (keyLength);
            return kpg.genKeyPair ();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static PublicKey getPublicKey (byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec (keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance (ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic (keySpec);
        return publicKey;
    }

    public static PublicKey getPublicKey (String modulus, String publicExponent)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        BigInteger bigIntModulus = new BigInteger (modulus);
        BigInteger bigIntPrivateExponent = new BigInteger (publicExponent);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec (bigIntModulus, bigIntPrivateExponent);
        KeyFactory keyFactory = KeyFactory.getInstance (ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic (keySpec);
        return publicKey;
    }

    public static PublicKey loadPublicKey (String publicKeyStr) throws Exception {
        byte[] buffer = Base64.decodeBase64 (publicKeyStr);
        KeyFactory keyFactory = KeyFactory.getInstance (ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec (buffer);
        return keyFactory.generatePublic (keySpec);
    }

    public static PublicKey loadPublicKey (InputStream in) throws Exception {
        return loadPublicKey (readKey (in));
    }


    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        byte[] buffer = Base64.decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        return keyFactory.generatePrivate(keySpec);
    }

    private static String readKey (InputStream in) throws IOException {
        BufferedReader br = new BufferedReader (new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder ();
        while ((readLine = br.readLine ()) != null) {
            if (readLine.charAt (0) == '-') {
                continue;
            } else {
                sb.append (readLine);
                sb.append ('\r');
            }
        }

        return sb.toString ();
    }


}
