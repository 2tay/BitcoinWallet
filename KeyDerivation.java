import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import models.*;
import utility.*;

public class KeyDerivation {

    private static final BigInteger CURVE_ORDER = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F", 16);

    public static KeyPair deriveHardenedChildKey(KeyPair parentKey, int index) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] parentPrivateKeyBytes = parentKey.getPrivateKey();
        ByteBuffer buffer = ByteBuffer.allocate(parentPrivateKeyBytes.length + 5); // Fix allocation size
        buffer.put((byte) 0); // Add a zero byte to the start for private key
        buffer.put(parentPrivateKeyBytes);
        buffer.putInt(index + 0x80000000); // Add the hardened bit to the index

        byte[] data = buffer.array();
        byte[] hmacResult = HmacSha512.hmacSha512(parentKey.getChainCode(), data);

        byte[] il = Arrays.copyOfRange(hmacResult, 0, 32);
        byte[] ir = Arrays.copyOfRange(hmacResult, 32, 64);

        BigInteger ilInt = new BigInteger(1, il);
        BigInteger parentKeyInt = new BigInteger(1, parentPrivateKeyBytes);

        BigInteger childKeyInt = ilInt.add(parentKeyInt).mod(CURVE_ORDER);
        byte[] childPrivateKey = childKeyInt.toByteArray();
        byte[] childChainCode = ir;

        return new KeyPair(childPrivateKey, childChainCode);
    }
}
