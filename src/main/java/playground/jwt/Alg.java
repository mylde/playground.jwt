package playground.jwt;

import org.apache.commons.codec.binary.Base64;
import playground.jwt.entity.Header;
import playground.jwt.entity.Payload;
import playground.jwt.key.KeyPair;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 *
 */
public interface Alg {
    String headerVal();
    String sign(Header h, Payload p) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException;

    class RS256 implements Alg {
        private KeyPair kp;
        static final String DESC = "SHA256withRSA";
        public RS256(KeyPair kp) {
            this.kp = kp;
        }
        @Override
        public String headerVal() {
            return "RS256";
        }
        @Override
        public String sign(Header h, Payload p) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
            Signature signature = Signature.getInstance(DESC);
            signature.initSign(kp.priKey());
            String hs = Base64.encodeBase64URLSafeString(h.toJson().getBytes(UTF_8));
            String cs = Base64.encodeBase64URLSafeString(p.toJson().getBytes(UTF_8));
            signature.update((hs + "." + cs).getBytes(UTF_8));
            String sig = Base64.encodeBase64URLSafeString(signature.sign());
            return String.format("%s.%s.%s", hs, cs, sig);
        }
    }

    class ES256 implements Alg {
        public ES256() {
            throw new UnsupportedOperationException();
        }
        @Override
        public String headerVal() {
            return null;
        }
        @Override
        public String sign(Header h, Payload p) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
            return null;
        }
    }

    class HS256 implements Alg {
        public HS256() {
            throw new UnsupportedOperationException();
        }
        @Override
        public String headerVal() {
            return null;
        }
        @Override
        public String sign(Header h, Payload p) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
            return null;
        }
    }
}
