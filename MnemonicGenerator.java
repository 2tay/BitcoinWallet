import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import utility.*;

public class MnemonicGenerator {
    private static final String[] WORD_LIST = Bip39Wordlist.getWord_list();
    private static final int[] VALID_ENTROPY_LENGTHS = {128, 160, 192, 224, 256};

    public static String generateMnemonic(int entropyBits) throws IllegalArgumentException, NoSuchAlgorithmException {
        if (!Arrays.stream(VALID_ENTROPY_LENGTHS).anyMatch(x -> x == entropyBits)) {
            throw new IllegalArgumentException("Invalid entropy length. Must be one of " + Arrays.toString(VALID_ENTROPY_LENGTHS));
        }

        byte[] entropy = generateEntropy(entropyBits);
        byte[] hash = sha256(entropy);
        int checksumLength = entropyBits / 32;

        StringBuilder bits = new StringBuilder();
        for (byte b : entropy) {
            bits.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        bits.append(bytesToBinary(hash).substring(0, checksumLength));

        StringBuilder mnemonic = new StringBuilder();
        for (int i = 0; i < bits.length(); i += 11) {
            String wordIndex = bits.substring(i, i + 11);
            int index = Integer.parseInt(wordIndex, 2);
            mnemonic.append(WORD_LIST[index]).append(" ");
        }

        return mnemonic.toString().trim();
    }

    private static byte[] generateEntropy(int bits) {
        byte[] entropy = new byte[bits / 8];
        new SecureRandom().nextBytes(entropy);
        return entropy;
    }

    private static byte[] sha256(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input);
    }

    private static String bytesToBinary(byte[] bytes) {
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            binary.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return binary.toString();
    }

    public static void main(String[] args) {
        try {
            String mnemonic = generateMnemonic(128); // Generate a 12-word mnemonic
            System.out.println(mnemonic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
