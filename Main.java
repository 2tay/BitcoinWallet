import models.*;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        try {
            // Example parent key and chain code
            byte[] parentPrivateKey = new BigInteger("1E99423A4ED27608A15A2616EBF71B202D6C7DAA6FB6A3712F2D7EBC7DE4AEE4", 16).toByteArray();
            byte[] parentChainCode = new BigInteger("873dff81c02f525623fd1fe518d93f238c6d5d3b3f663be0a2e2d919e2b96d59", 16).toByteArray();

            KeyPair parentKey = new KeyPair(parentPrivateKey, parentChainCode);

            // Derive a hardened child key at index 0
            KeyPair childKey = KeyDerivation.deriveHardenedChildKey(parentKey, 0);

            // Print the child key and chain code as hexadecimal strings
            System.out.println("Child Private Key: " + bytesToHex(childKey.getPrivateKey()));
            System.out.println("Child Chain Code: " + bytesToHex(childKey.getChainCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
