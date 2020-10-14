package playground.jwt.entity;

import com.google.gson.Gson;

/**
 *
 */
public class Header implements JsonSerializable {
    public String typ;
    public String alg;

    public Header() {
        typ = "JWT";
    }
    public Header(String alg) {
        typ = "JWT";
        this.alg = alg;
    }
    public Header(String typ, String alg) {
        this.typ = typ;
        this.alg = alg;
    }
    public String toJson() {
        return new Gson().toJson(this);
    }
}
