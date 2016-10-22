/**
 * 
 */
package base.util;


import javax.crypto.Cipher;  
import javax.crypto.spec.IvParameterSpec;  
import javax.crypto.spec.SecretKeySpec;  

import org.apache.commons.codec.binary.Base64;
  

public class AES 
{  
  
    
    public static String Encrypt(String sSrc, String sKey) throws Exception {  
        if (sKey == null) {  
            System.out.print("KeyÎª¿Õnull");  
            return null;  
        }  
        
        if (sKey.length() != 16) {  
            System.out.print("Key³¤¶È²»ÊÇ16Î»");  
            return null;  
        }  
        byte[] raw = sKey.getBytes();  
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());  
  
        return Base64.encodeBase64String(encrypted);
    }
  
  
    public static String Decrypt(String sSrc, String sKey) throws Exception {  
        try {  
              
            if (sKey == null) {  
                System.out.print("Key Null");  
                return null;  
            }  
            
            if (sKey.length() != 16) {  
                System.out.print("Key lenght is not 16");  
                return null;  
            }  
            byte[] raw = sKey.getBytes("ASCII");  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec("0102030405060708"  
                    .getBytes());  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = Base64.decodeBase64(sSrc);  
            try {  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original);  
                return originalString;  
            } catch (Exception e) {  
                System.out.println(e.toString());  
                return null;  
            }  
        } catch (Exception ex) {  
            System.out.println(ex.toString());  
            return null;  
        }  
    }  
    
    
    public static void main(String[] unused) throws Exception {
    	
        
        String json="[{\"id\":379,\"key\":\"DISTRICT\",\"code\":\"110101\",\"value\":\"东城区\",\"status\":1},{\"id\":380,\"key\":\"DISTRICT\",\"code\":\"110102\",\"value\":\"西城区\",\"status\":1},{\"id\":381,\"key\":\"DISTRICT\",\"code\":\"110103\",\"value\":\"崇文区\",\"status\":1},{\"id\":382,\"key\":\"DISTRICT\",\"code\":\"110104\",\"value\":\"宣武区\",\"status\":1},{\"id\":383,\"key\":\"DISTRICT\",\"code\":\"110105\",\"value\":\"朝阳区\",\"status\":1}]";
        byte[] dataBytes =json.getBytes("utf-8");
        String sKey="vda@#$^&%!123456";
        String encoded=AES.Encrypt(json, sKey);
        System.out.println("encoded->"+new String( encoded));
        
        String decoded = AES.Decrypt(encoded, sKey);
        System.out.println("decoded->"+new String(decoded));
    }
  

}  
