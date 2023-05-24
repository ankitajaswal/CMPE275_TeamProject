package cmpe275.wiors.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Activation {
	
    private static final String SECRET_KEY = "CMPE-275-KEY-123"; // Secret key for encryption

    public Activation()
    {
    	
    }    

    public String encodeEmail(String email) {
        try {
            // Encrypt the email address
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(email.getBytes(StandardCharsets.UTF_8));

            // Encode the encrypted bytes to a mappable string
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String decodeEmail(String encodedEmail) {
        try {
            // Decode the mappable string to encrypted bytes
            byte[] encryptedBytes = Base64.getDecoder().decode(encodedEmail);

            // Decrypt the encrypted bytes back to the original email address
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    
    public static void main(String[] args) {
        String email = "example@example.com";

    	Activation activation = new Activation();
        
        // Encode email address to a mappable string
        String encodedEmail = activation.encodeEmail(email);
        System.out.println("Encoded email: " + encodedEmail);

        // Decode mappable string back to the original email address
        String decodedEmail = activation.decodeEmail(encodedEmail);
        System.out.println("Decoded email: " + decodedEmail);
    }
    
    
}
