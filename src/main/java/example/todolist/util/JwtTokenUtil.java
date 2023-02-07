package example.todolist.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private static final String SECRET =
            "ItIsSecretKeyItNeedsToooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooLongKey";

    private static final long TOKEN_VALID_PERIOD = 20 * 1000;

    public String getSubjectFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    private Claims getAllClaimsFromToken(String token) {
        System.out.println("insdie getAllClaimsFromToken");
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String phone) {
        long currentMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(phone)
                .setIssuedAt(new Date(currentMillis))
                .setExpiration(new Date(currentMillis + TOKEN_VALID_PERIOD))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
