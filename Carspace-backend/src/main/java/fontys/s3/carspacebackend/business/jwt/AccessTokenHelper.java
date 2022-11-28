package fontys.s3.carspacebackend.business.jwt;

import fontys.s3.carspacebackend.domain.AccessToken;
import fontys.s3.carspacebackend.domain.User;
import fontys.s3.carspacebackend.exception.BadTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessTokenHelper implements IAccessTokenHelper{
    private final Key key;

    public AccessTokenHelper(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateAccessToken(User user) {
        Long userId = user.getId();
        String role= user.getRole().getRole();

        return encode(
                AccessToken.builder()
                        .username(user.getUsername())
                        .role(role)
                        .userId(userId)
                        .build());
    }

    private String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getRole() != null || !accessToken.getRole().isEmpty()) {
            claimsMap.put("role", accessToken.getRole());
        }
        if (accessToken.getUserId() != null) {
            claimsMap.put("userId", accessToken.getUserId());
        }

        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }
    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessTokenEncoded).getBody();

            String role = claims.get("role", String.class);

            return AccessToken.builder()
                    .username(claims.getSubject())
                    .role(role)
                    .userId(claims.get("userId", Long.class))
                    .build();
        } catch (JwtException e) {
            throw new BadTokenException(e.getMessage());
        }
    }
}
