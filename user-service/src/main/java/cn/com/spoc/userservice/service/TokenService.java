package cn.com.spoc.userservice.service;

import cn.com.spoc.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    private static final String JWT_SECRET = "====ThisIsSpocWebsiteJwtSecretKeyUsedToCheckIdentityOfUser====";
    private static final int JWT_EXPIRATION_IN_MS = 604800000;
    @Autowired
    private UserRepository userRepository;

    public String generateToken(String username) {
        var now = new Date();
        var expiryTime = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);
        var identity = userRepository.findIdentityByUsername(username);
        System.out.println(username);
        return Jwts.builder()
                .setClaims(Map.of("identity", identity))
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryTime)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isTokenValid(String token) {
        var claims = getClaimsFromToken(token);
        return isTokenValid(claims);
    }

    public boolean isTokenValid(Claims claims) {
        if (claims == null) {
            return false;
        }
        return claims.getExpiration()
                .after(new Date());
    }

    public String getUsernameFromToken(String token) {
        var claims = getClaimsFromToken(token);
        if (!isTokenValid(claims)) {
            return "";
        }
        return claims.getSubject();
    }

    public String getIdentityFromToken(String token) {
        var claims = getClaimsFromToken(token);
        if (!isTokenValid(claims)) {
            return "";
        }
        return (String) claims.get("identity");
    }
}
