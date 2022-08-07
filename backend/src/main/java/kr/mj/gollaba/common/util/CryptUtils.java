package kr.mj.gollaba.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CryptUtils {

    private final String encryptKey;

    public CryptUtils(@Value("${security.aes.encrypt-key}") String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public byte[] encryptToBytes(String strToEncrypt) {
        SecretKeySpec secretKey = new SecretKeySpec(encryptKey.getBytes(), "AES");
        byte[] result = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            result = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String encrypt(String strToEncrypt)  {
        String encryptedStr = Base64.getEncoder().encodeToString(encryptToBytes(strToEncrypt));
        return encryptedStr;
    }

    public byte[] decryptToBytes(String key, byte[] bytesToDecrypt) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return  cipher.doFinal(bytesToDecrypt);
    }

    public String decrypt(String strToDecrypt) throws Exception {
        byte[] bytesToDecrypt = Base64.getDecoder().decode(strToDecrypt);
        String decreptedStr = new String(decryptToBytes(encryptKey, bytesToDecrypt));
        return decreptedStr;
    }
}
