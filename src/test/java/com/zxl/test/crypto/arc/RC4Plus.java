package com.zxl.test.crypto.arc;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.liepin.common.other.StringUtil;

public class RC4Plus {
    private static final String CIPHER_ALGORITHM = "RC4";

    private static RC4Plus instance = new RC4Plus();

    private RC4Plus() {
    }

    public static RC4Plus getInstance() {
        return instance;
    }

    /**
     * 加密
     * 
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public String encrypt(String content, String key) throws Exception {
        if (content == null || key == null) {
            return null;
        }
        Cipher cipher = getEncrypt(key);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        return StringUtil.byteArr2HexStr(encrypted);
    }

    /**
     * 解密
     * <p>
     * 密钥需要16位
     * 
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public String decrypt(String content, String key) throws Exception {
        if (content == null || key == null) {
            return null;
        }
        Cipher cipher = getDecrypt(key);
        byte[] encrypted = StringUtil.hexStr2ByteArr(content);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original, "utf-8");
    }

    private Cipher getEncrypt(String key) throws Exception {
        /**
         * 生成密钥
         */
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), CIPHER_ALGORITHM);
        /**
         * 创建加密密码器
         */
        Cipher encryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec); // 初始化，设置为加密模式
        return encryptCipher;

    }

    private Cipher getDecrypt(String key) throws Exception {
        /**
         * 生成密钥
         */
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), CIPHER_ALGORITHM);
        /**
         * 创建解密密码器
         */
        Cipher decryptCipher = Cipher.getInstance(CIPHER_ALGORITHM);
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec); // 初始化，设置为解密模式
        return decryptCipher;
    }

}
