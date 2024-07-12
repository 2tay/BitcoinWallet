package models;

public class KeyPair {
    private final byte[] privateKey;
    private final byte[] chainCode;

    public KeyPair(byte[] privateKey, byte[] chainCode) {
        this.privateKey = privateKey;
        this.chainCode = chainCode;
    }

    public byte[] getPrivateKey() {
        return privateKey;
    }

    public byte[] getChainCode() {
        return chainCode;
    }
}
