package playground.jwt.entity;

import playground.jwt.Alg;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

/**
 *
 */
public class JWT {
    Header h;
    Payload p;
    public JWT(Header h, Payload p) {
        this.h = h;
        this.p = p;
    }
    public String sign(Alg alg) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        if (h.alg == null) {
            h.alg = alg.headerVal();
        }
        return alg.sign(h, p);
    }
}
