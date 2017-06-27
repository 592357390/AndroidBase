package com.handarui.baselib.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xubo on 2017/6/19.
 */

public class AESEncryptUtil {

    private static final String AES = "AES";

    public AESEncryptUtil() {
    }

    public static byte[] encrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(1, securekey);
        return cipher.doFinal(src);
    }

    public static byte[] decrypt(byte[] src, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        SecretKeySpec securekey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(2, securekey);
        return cipher.doFinal(src);
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }

        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if (b.length % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        } else {
            byte[] b2 = new byte[b.length / 2];

            for (int n = 0; n < b.length; n += 2) {
                String item = new String(b, n, 2);
                b2[n / 2] = (byte) Integer.parseInt(item, 16);
            }

            return b2;
        }
    }

    public static final String decrypt(String data, String CRYPT_KEY) {
        try {
            return new String(decrypt(hex2byte(data.getBytes()), CRYPT_KEY));
        } catch (Exception var3) {
            return null;
        }
    }

    public static final String encrypt(String data, String CRYPT_KEY) {
        try {
            return byte2hex(encrypt(data.getBytes(), CRYPT_KEY));
        } catch (Exception var3) {
            return null;
        }
    }

    public static void main(String[] args) {
        String AESKey = "1234567890123456";
        String content = "611111";
        System.out.println("加密前：" + content);
        String idEncrypt = encrypt(content, AESKey);
        System.out.println("加密后：" + idEncrypt);
        String idDecrypt = decrypt(idEncrypt, AESKey);
        System.out.println("解密后：" + idDecrypt);
    }
}

