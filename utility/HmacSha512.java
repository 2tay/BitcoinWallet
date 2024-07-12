package utility;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

public class HmacSha512 {

    private static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";

    public static byte[] hmacSha512(byte[] key, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_SHA512_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key, HMAC_SHA512_ALGORITHM);
        mac.init(keySpec);
        return mac.doFinal(data);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        try {
            // Example key and data
            byte[] key = "your-secret-key".getBytes();
            byte[] data = "your-data".getBytes();

            // Compute HMAC-SHA512
            byte[] hmacResult = hmacSha512(key, data);

            // Print the result as a hex string
            System.out.println("HMAC-SHA512: " + bytesToHex(hmacResult));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }
}
