package playground.jwt.key;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;

/**
 *
 */
public class EC implements KeyPair {
    java.security.KeyPair kp;
    public EC() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpj = KeyPairGenerator.getInstance("EC");
        kpj.initialize(new ECGenParameterSpec("secp256r1"));
        kp = kpj.genKeyPair();
    }
    @Override
    public PublicKey pubKey() {
        return kp.getPublic();
    }
    @Override
    public PrivateKey priKey() {
        return kp.getPrivate();
    }
}
