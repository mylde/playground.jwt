package playground.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import playground.jwt.entity.Header;
import playground.jwt.entity.JWT;
import playground.jwt.entity.Payload;
import playground.jwt.key.KeyPair;
import playground.jwt.key.RSA;

import java.nio.charset.StandardCharsets;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TokenTest {
    @Test
    public void same_token_as_lib_rs256() throws Exception {
        KeyPair kp = new RSA();
        Instant i = LocalDateTime.of(2100, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
        Date exp = Date.from(i);

        String tokenLib;
        {
            // created by java-jwt
            RSAKeyProvider keyProvider = new RSAKeyProvider() {
                @Override
                public RSAPublicKey getPublicKeyById(String keyId) {
                    return (RSAPublicKey) kp.pubKey();
                }
                @Override
                public RSAPrivateKey getPrivateKey() {
                    return (RSAPrivateKey) kp.priKey();
                }
                @Override
                public String getPrivateKeyId() {
                    return null;
                }
            };
            Algorithm alg = Algorithm.RSA256(keyProvider);
            Map<String, Object> headers = new HashMap<>();
            headers.put("alg", "RS256");
            headers.put("typ", "JWT");
            tokenLib = com.auth0.jwt.JWT.create()
                    .withHeader(headers)
                    .withSubject("subject0123")
                    .withExpiresAt(exp)
                    .withIssuer("issuer0123")
                    .withClaim("user_id", "1")
                    .sign(alg);
        }

        String token;
        {
            Alg alg = new Alg.RS256(kp);
            Header h = new Header();
            Payload c = new Payload();
            c.withSubject("subject0123");
            c.withExpirationDate(exp);
            c.withIssuer("issuer0123");
            c.withClaim("user_id", "1");
            JWT j = new JWT(h, c);
            token = j.sign(alg);
        }

        assertEquals(tokenLib, token);
    }

    @Test
    public void try_to_verify() throws Exception {
        KeyPair kp = new RSA();
        Instant i = LocalDateTime.of(2100, 1, 1, 0, 0, 0).toInstant(ZoneOffset.UTC);
        Date exp = Date.from(i);

        Alg alg = new Alg.RS256(kp);
        Header h = new Header();
        Payload c = new Payload();
        c.withSubject("subject0123");
        c.withExpirationDate(exp);
        c.withIssuer("issuer0123");
        c.withClaim("user_id", "1");
        JWT t = new JWT(h, c);
        String token = t.sign(alg);

        String[] sp = token.split("\\.", 3);
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(kp.pubKey());
        signature.update(String.format("%s.%s", sp[0], sp[1]).getBytes(StandardCharsets.UTF_8));
        assertTrue(signature.verify(Base64.decodeBase64(sp[2])));
    }
}
