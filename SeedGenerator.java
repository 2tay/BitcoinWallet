import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.nio.charset.StandardCharsets;

public class SeedGenerator {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int ITERATIONS = 2048;
    private static final int KEY_LENGTH = 512;

    public static byte[] generateSeed(String mnemonic, String passphrase) 
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = "mnemonic" + passphrase;
        char[] mnemonicChars = mnemonic.toCharArray();
        byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);

        PBEKeySpec spec = new PBEKeySpec(mnemonicChars, saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        
        return skf.generateSecret(spec).getEncoded();
    }

    public static void main(String[] args) {
        try {
            String mnemonic = MnemonicGenerator.generateMnemonic(128);
            String passphrase = "hakim"; // Use an empty string if no passphrase

            System.out.println("mnemonic: " + mnemonic );
            System.out.println("passphrase: "+ passphrase);

            byte[] seed = generateSeed(mnemonic, passphrase);

            // Print the seed as a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : seed) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            System.out.println("Seed: " + hexString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}