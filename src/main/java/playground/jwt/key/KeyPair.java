package playground.jwt.key;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 */
public interface KeyPair {
    public PublicKey pubKey();
    public PrivateKey priKey();
}
