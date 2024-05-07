package baeksaitong.sofp.global.jwt;

import baeksaitong.sofp.global.common.service.CustomMemberDetailsService;
import baeksaitong.sofp.global.jwt.error.CustomJwtException;
import baeksaitong.sofp.global.jwt.error.code.JwtErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import static baeksaitong.sofp.global.common.Constants.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-valid-minute}")
    private long accessTokenValidTime;
    private SecretKeySpec secretKeySpec;

    private final CustomMemberDetailsService memberDetailsService;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes());
        this.secretKeySpec = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        accessTokenValidTime = accessTokenValidTime * 60 * 1000L;
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date validity = new Date(now.getTime() + this.accessTokenValidTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(JWT_AUTHORITIES_KEY, authorities)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String userId = getUserId(token);
        UserDetails principal = memberDetailsService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            return null;
        }
        return authorization.substring("Bearer ".length());
    }

    public boolean validateAccessToken(String jwtToken) {
        if (jwtToken == null) {
            throw new CustomJwtException(JwtErrorCode.EMPTY_TOKEN);
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKeySpec)  // Update to use parserBuilder()
                    .build()
                    .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            log.info("JWT 토큰이 유효하지 않습니다.");
            throw new CustomJwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다.");
            throw new CustomJwtException(JwtErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
            throw new CustomJwtException(JwtErrorCode.UNSUPPORTED_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
