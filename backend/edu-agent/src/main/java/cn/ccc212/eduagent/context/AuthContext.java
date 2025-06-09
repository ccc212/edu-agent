package cn.ccc212.eduagent.context;

import cn.ccc212.eduagent.constant.JwtClaimsConstant;
import io.jsonwebtoken.Claims;

public class AuthContext {

    private static final ThreadLocal<Claims> JWT_CLAIMS = new ThreadLocal<>();

    /**
     * JWT相关操作
     */
    public static void setJwtClaims(Claims claims) {
        JWT_CLAIMS.set(claims);
    }

    public static Claims getJwtClaims() {
        return JWT_CLAIMS.get();
    }

    public static void removeJwtClaims() {
        JWT_CLAIMS.remove();
    }

    public static Long getUserId() {
        return getJwtClaims().get(JwtClaimsConstant.USER_ID, Long.class);
    }

}
