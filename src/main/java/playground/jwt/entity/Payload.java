package playground.jwt.entity;

import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Payload implements JsonSerializable {
    //    private Map<String, Object> claims = new TreeMap<>();
    private Map<String, Object> claims = new HashMap<>();
    public void withIssuer(String issuer) {
        withClaim("iss", issuer);
    }
    public void withSubject(String subject) {
        withClaim("sub", subject);
    }
    public void withAudience(String audience) {
        withClaim("aud", audience);
    }
    public void withExpirationDate(Date expirationDate) {
        withClaim("exp", expirationDate.getTime() / 1000L);
    }
    public void withNotBefore(Date notBefore) {
        withClaim("nbf", notBefore.getTime() / 1000L);
    }
    public void withIssuedAt(Date issuedAt) {
        withClaim("iat", issuedAt.getTime() / 1000L);
    }
    public void withJwtId(String jwtId) {
        withClaim("jti", jwtId);
    }
    public void withClaim(String key, Object value) {
        claims.put(key, value);
    }
    @Override
    public String toJson() {
        return new Gson().toJson(claims);
    }
}
