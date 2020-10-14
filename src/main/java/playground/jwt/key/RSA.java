package playground.jwt.key;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAKeyGenParameterSpec;

/**
 *
 */
public class RSA implements KeyPair {
    java.security.KeyPair kp;
    public RSA() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator kpj = KeyPairGenerator.getInstance("RSA");
        kpj.initialize(new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4));
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
