/**
 * 
 */
package base.util;


import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import base.exception.BusinessException;



public class AESCryptographyProvider  {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESCryptographyProvider.class);
    private static String TRANSFORM = "AES/CBC/PKCS5Padding";
    private static String ALGORITHM = "AES";
    private static SecretKeySpec secretKeySpec;
    private static byte[] initialVector  = { 0x0a, 0x0d, 0x05, 0x03, 0x04, 0x0b, 0x0e, 0x0d,0x0a, 0x01, 0x02, 0x03, 0x07, 0x0b, 0x0c, 0x0d };
       
    public AESCryptographyProvider(String base64EncodedKey) {
    	 System.out.println("key->"+org.apache.commons.codec.binary.Base64.decodeBase64(base64EncodedKey.getBytes()));
        secretKeySpec = new SecretKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(base64EncodedKey.getBytes()), "AES");
        
    }
    
    
    public static  byte[] encryptString(byte[] bytes) throws BusinessException{
    	return Base64.encode(encrypt(bytes));
    	
    }
    
    
    public static byte[] decryptString(byte[] encoded) throws BusinessException{
    	return decrypt(Base64.decode(encoded)); 
    }
    
    
    public static byte[] encrypt(byte[] inputBytes) throws BusinessException {
        try {   
            Cipher cipher = Cipher.getInstance(AESCryptographyProvider.TRANSFORM);
            IvParameterSpec ips = new IvParameterSpec(initialVector);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ips);
            return cipher.doFinal(inputBytes);
            
        } catch (Exception e) { 
            LOGGER.error("Unable to encrypt - " + e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }

    public  static byte[] decrypt(byte[] inputBytes) throws BusinessException {
        try {
            Cipher cipher = Cipher.getInstance(AESCryptographyProvider.TRANSFORM);
            IvParameterSpec ips = new IvParameterSpec(initialVector);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ips);
            return cipher.doFinal(inputBytes);
        } catch (Exception e) {
            LOGGER.warn("Unable to decrypt reason '{}'",e.getMessage());
            throw new BusinessException(e.getMessage());
        }
    }
   
    public static byte[] generateBase64Key() {
        try {
            KeyGenerator  keyGenerator  = KeyGenerator.getInstance(AESCryptographyProvider.ALGORITHM);
            SecretKey     key           = keyGenerator.generateKey();
           
            return org.apache.commons.codec.binary.Base64.encodeBase64(key.getEncoded(),false,true);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Unable to generate key - " + e.getMessage());
            return null;
        }
    }
   
    public static void main(String[] unused) throws Exception {
    	String secretKey = new String(AESCryptographyProvider.generateBase64Key());
    	 System.out.println("secretKey->"+secretKey);
        AESCryptographyProvider crypto = new AESCryptographyProvider(secretKey);

        
        String json="[{\"id\":379,\"key\":\"DISTRICT\",\"code\":\"110101\",\"value\":\"东城区\",\"status\":1},{\"id\":380,\"key\":\"DISTRICT\",\"code\":\"110102\",\"value\":\"西城区\",\"status\":1},{\"id\":381,\"key\":\"DISTRICT\",\"code\":\"110103\",\"value\":\"崇文区\",\"status\":1},{\"id\":382,\"key\":\"DISTRICT\",\"code\":\"110104\",\"value\":\"宣武区\",\"status\":1},{\"id\":383,\"key\":\"DISTRICT\",\"code\":\"110105\",\"value\":\"朝阳区\",\"status\":1}]";
        byte[] dataBytes =
            json.getBytes("utf-8");

        byte[] encoded=crypto.encryptString(dataBytes);
        System.out.println("encoded->"+new String( encoded));
        
        byte[] decBytes = crypto.decryptString(encoded);
        System.out.println("decoded->"+new String(decBytes));
    }
}
