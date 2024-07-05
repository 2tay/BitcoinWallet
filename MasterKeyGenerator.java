import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import models.MasterKey;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.util.Arrays;

public class MasterKeyGenerator {

    private static final String HMAC_SHA512_ALGORITHM = "HmacSHA512";
    private static final String HMAC_KEY = "Bitcoin seed";

    public static MasterKey generateMasterKey(byte[] seed) 
            throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance(HMAC_SHA512_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(HMAC_KEY.getBytes(StandardCharsets.UTF_8), HMAC_SHA512_ALGORITHM);
        hmacSha512.init(keySpec);

        byte[] hmacResult = hmacSha512.doFinal(seed);

        byte[] masterPrivateKey = Arrays.copyOfRange(hmacResult, 0, 32);
        byte[] masterChainCode = Arrays.copyOfRange(hmacResult, 32, 64);

        return new MasterKey(masterPrivateKey, masterChainCode);
    }

    public static void main(String[] args) {
        try {
            String mnemonic = MnemonicGenerator.generateMnemonic(128);
            String passphrase = ""; // Use an empty string if no passphrase

            byte[] seed = SeedGenerator.generateSeed(mnemonic, passphrase);

            MasterKey masterKey = generateMasterKey(seed);

            System.out.println("Mnemonic: " + mnemonic);
            System.out.println("Passphrase: " + passphrase);
            System.out.println("Seed: " + bytesToHex(seed));
            System.out.println("Master Private Key: " + bytesToHex(masterKey.getPrivateKey()));
            System.out.println("Master Chain Code: " + bytesToHex(masterKey.getChainCode()));
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
