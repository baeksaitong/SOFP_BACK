package baeksaitong.sofp.global.jwt;

import baeksaitong.sofp.global.common.service.CustomMemberDetailsService;
import baeksaitong.sofp.global.jwt.error.CustomJwtException;
import baeksaitong.sofp.global.jwt.error.code.JwtErrorCode;
import baeksaitong.sofp.global.redis.RedisService;
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

import static baeksaitong.sofp.global.jwt.JwtConstants.*;
import static baeksaitong.sofp.global.redis.RedisPrefix.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-valid-minute}")
    private long accessTokenValidTime;
    @Value("${jwt.refresh-token-valid-month}")
    private long refreshTokenValidTime;
    private SecretKeySpec secretKeySpec;

    private final CustomMemberDetailsService memberDetailsService;
    private final RedisService redisService;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes());
        this.secretKeySpec = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());

        accessTokenValidTime = accessTokenValidTime * 60 * 1000L;
        refreshTokenValidTime = refreshTokenValidTime * 30 * 24 * 60 * 60 * 1000L;
    }

    public String createAccessToken(Authentication authentication) {
        return createJwtToken(authentication, accessTokenValidTime, ACCESS_TOKEN_TYPE.getValue());
    }

    public String createRefreshToken(Authentication authentication) {
        String token = createJwtToken(authentication, accessTokenValidTime, REFRESH_TOKEN_TYPE.getValue());

        redisService.save(REFRESH_TOKEN, authentication.getName(), token, REFRESH_TOKEN.getDuration());
        return token;

    }

    private String createJwtToken(Authentication authentication, Long validityTime, String tokenType) {
        String authorities = createAuthorities(authentication);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityTime);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(TOKEN_TYPE.getValue(), tokenType)
                .claim(AUTHORITIES_KEY.getValue(), authorities)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
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

    public Date getExpiration(String jwtToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build()
                .parseClaimsJws(jwtToken);

        return claims.getBody().getExpiration();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            return null;
        }
        return authorization.substring("Bearer ".length());
    }

    public void validateRefreshToken(String refreshToken, String userId) {
        validateToken(refreshToken);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)  // Update to use parserBuilder()
                .build()
                .parseClaimsJws(refreshToken);
        String tokenType = claims.getBody().get(TOKEN_TYPE.getValue(), String.class);

        if(!tokenType.equals(REFRESH_TOKEN_TYPE.getValue())){
            throw new CustomJwtException(JwtErrorCode.INVALID_REFRESH);
        }

        if (redisService.hasNoKey(REFRESH_TOKEN, userId)) {
            throw new CustomJwtException(JwtErrorCode.CANNOT_REFRESH);
        }

        String redisToken = (String) redisService.get(REFRESH_TOKEN, userId);

        if (!redisToken.equals(refreshToken)) {
            throw new CustomJwtException(JwtErrorCode.INVALID_JWT_TOKEN);
        }
    }

    public boolean validateToken(String jwtToken) {
        if (jwtToken == null) {
            throw new CustomJwtException(JwtErrorCode.EMPTY_TOKEN);
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKeySpec)  // Update to use parserBuilder()
                    .build()
                    .parseClaimsJws(jwtToken);
            Claims claimsBody = claims.getBody();

            return !claimsBody.getExpiration().before(new Date()) && claimsBody.get(TOKEN_TYPE.getValue(), String.class).equals(ACCESS_TOKEN_TYPE.getValue());
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
