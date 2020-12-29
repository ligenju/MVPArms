package com.example.mvparms.utils;

import android.util.Base64;


import com.example.mvparms.constants.AuthKey;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSACodeHelper {
    private static final String TAG = "RSACodeHelper";
    private static final String RSATYPE = "RSA/ECB/PKCS1Padding"; //Cipher必须用这种类型
    public static PublicKey mPublicKey; //这里要注意一下，原来用的类型是RSAPublicKey 但死活就是解不了服务端私钥加密的密文改成PublicKey就可以了
    private RSACodeHelper() {

    }

    private static RSACodeHelper rsaCodeHelper;

    public static RSACodeHelper getInstance() {
        if (rsaCodeHelper == null) {
            synchronized (RSACodeHelper.class) {
                if (rsaCodeHelper == null) {
                    rsaCodeHelper = new RSACodeHelper();
                    init();
                }
            }
        }
        return rsaCodeHelper;
    }

    private static void init() {
        KeyPairGenerator keyPairGen = null;
        try {
            //设置使用哪种加密算法  
            keyPairGen = KeyPairGenerator.getInstance("RSA");
            //密钥位数  
            keyPairGen.initialize(1024); //一定要和服务端的长度保持一致  
            //公钥  
            try {
                if (mPublicKey == null) {
                    mPublicKey = RSA.loadPublicKey(AuthKey.publicKey);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  MyLog.i(TAG,"RSA 构造函数完成");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得公钥
     *
     * @param key 公钥字符串
     * @return 返回公钥
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = base64Dec(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 取得私钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes = base64Dec(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;

    }

    /**
     * 使用客户端公钥加密字符串
     *
     * @param str 需要加密的字符串
     * @return 密文
     */
    public String cPubEncrypt(String str) {
        String strEncrypt = null;

        //实例化加解密类
        try {
            Cipher cipher = Cipher.getInstance(RSATYPE);
            //明文  
            byte[] plainText = str.getBytes();
            //加密  
            cipher.init(Cipher.ENCRYPT_MODE, mPublicKey);
            //将明文转化为根据公钥加密的密文，为byte数组格式  
            byte[] enBytes = cipher.doFinal(plainText);
            strEncrypt = base64Enc(enBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {
            return strEncrypt;
        }
    }

    /**
     * base64编码
     *
     * @param enBytes
     * @return
     */
    public static String base64Enc(byte[] enBytes) {
        return Base64.encodeToString(enBytes, Base64.DEFAULT);
    }

    /**
     * base64解码
     *
     * @param str
     * @return
     */
    public static byte[] base64Dec(String str) {
        return Base64.decode(str, Base64.DEFAULT);
    }

}  