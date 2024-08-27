package site.gun.emotion_calendar.user.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import site.gun.emotion_calendar.user.domain.CustomUserDetails;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration}")
    private Long expiration;


    public String generateToken(CustomUserDetails user) {
        if (user == null || !StringUtils.hasText(user.getUsername()) || user.getAuthorities() == null) {
            throw new IllegalArgumentException("사용자 정보가 유효하지 않습니다.");
        }

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        Optional<String> authority = user.getAuthorities().stream().findFirst().map(Object::toString);
        String authorityString = authority.orElse(null);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .claim("userId", user.getUserId())
                .claim("name",user.getName())
                .claim("roles", authorityString)
                .signWith(Keys.hmacShaKeyFor(secretKeyString.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean invalidateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKeyString.getBytes())).build()
                    .parseClaimsJws(token);
            Date now = new Date();
            Jwts.builder()
                    .setClaims(Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secretKeyString.getBytes())).build().parseClaimsJws(token).getBody())
                    .setExpiration(now)
                    .signWith(Keys.hmacShaKeyFor(secretKeyString.getBytes()), SignatureAlgorithm.HS512)
                    .compact();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
