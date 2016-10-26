/**
 * 
 */
package base.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author nijie
 *
 */
public class Decryptor {
	private static final String AESTYPE ="AES/ECB/PKCS5Padding"; 

	/**
	     * 解密
	     * @param keyStr
	     * @param encryptData
	     * @return
	     */
	    public static String AES_Decrypt(String keyStr, String encryptData) {
	        byte[] decrypt = null; 
	        try{ 
	            Key key = generateKey(keyStr); 
	            Cipher cipher = Cipher.getInstance(AESTYPE); 
	            cipher.init(Cipher.DECRYPT_MODE, key); 
	            decrypt = cipher.doFinal(Base64.decodeBase64(encryptData)); 
	        }catch(Exception e){ 
	           
	           e.printStackTrace();
	        } 
	        return new String(decrypt).trim(); 
	    } 

	    private static Key generateKey(String key)throws Exception{ 
	        try{            
	            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES"); 
	            return keySpec; 
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	            throw e; 
	        } 
	    } 
	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static byte[] decrypt(byte[] content, String password) {
		try {
			 KeyGenerator kgen = KeyGenerator.getInstance("AES"); 
             kgen.init(128, new SecureRandom(password.getBytes())); 
             SecretKey secretKey = kgen.generateKey(); 
             byte[] enCodeFormat = secretKey.getEncoded(); 
             SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");             
             Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化  
            byte[] result = cipher.doFinal(content); 
            return result; // 加密  

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 调用实例
	public static void main(String[] args) throws UnsupportedEncodingException {
		String content = "y6J5TVzmpmxkrX1LlmJVFECLesTR+W2b81bKU9lx1tmp1rd7ED7G80qYTZJRKbZnizOSu8Q3NqrbRGcqxyl6Kw==";
		String password = "c3mgof4bhcoo5nhm";
		// 加密
		// System.out.println("加密前：" + content);
		// byte[] encryptResult = encrypt(content, password);
		// String encryptResult64 = new String(Base64.getEncoder().encode(
		// encryptResult));
		// System.out.println("加密后base64：" + encryptResult64);
		// // 解密
		 byte[] decryptResult = decrypt(content.getBytes(), password);
		 System.out.println("解密后：" + new String(decryptResult, "UTF-8"));
	}
	 public static String AES_Encrypt(String keyStr, String plainText) { 
	        byte[] encrypt = null; 
	        try{ 
	            Key key = generateKey(keyStr); 
	            Cipher cipher = Cipher.getInstance(AESTYPE); 
	            cipher.init(Cipher.ENCRYPT_MODE, key); 
	            encrypt = cipher.doFinal(plainText.getBytes("UTF-8"));     
	        }catch(Exception e){ 
	            e.printStackTrace(); 
	        }
	        return new String(Base64.encodeBase64(encrypt)); 
	    } 
}
