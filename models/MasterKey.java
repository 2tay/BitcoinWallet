package models;
public class MasterKey {
    private final byte[] privateKey;
    private final byte[] chainCode;

    public MasterKey(byte[] privateKey, byte[] chainCode) {
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
