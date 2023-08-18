package com.common.security.api.jsonwebtoken;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class JsonWebTokenUtility {
    private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenUtility.class);

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public JsonWebTokenUtility() {
    }

    public static String createJsonWebToken(AuthTokenDetailsDTO authTokenDetailsDTO) {
        logger.info("createJsonWebToken()...");

        return Jwts.builder()
                .setSubject(authTokenDetailsDTO.getUserId())
                .claim("username", authTokenDetailsDTO.getUsername())
                .claim("email", authTokenDetailsDTO.getEmail())
                .signWith(signatureAlgorithm, getSecretKey()).compact();
    }

    private static Key deserializeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, signatureAlgorithm.getJcaName());
    }

    private static Key getSecretKey() {
        String encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";
        return deserializeKey(encodedKey);
    }

    public static AuthTokenDetailsDTO parseAndValidate(String token) {
        Claims claims = parseTokenIntoClaimsWithHandling(token);

        if (claims == null) {
            return null;
        }

        return createTokenDTOFromClaims(claims);
    }

    /**
     * For use when custom exception handling is needed
     *
     * @param token the token to be parsed
     * @return parsed auth token details
     * @throws Exception if anything goes wrong. Caller is responsible to handle any issues
     */
    public static AuthTokenDetailsDTO unsafeParseAndValidate(String token) throws Exception {
        Claims claims = parseTokenIntoClaims(token);

        if (claims == null) {
            return null;
        }

        return createTokenDTOFromClaims(claims);
    }

    private static Claims parseTokenIntoClaims(String token) throws Exception {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
    }

    private static Claims parseTokenIntoClaimsWithHandling(String token) {
        try {
            return parseTokenIntoClaims(token);
        } catch (ExpiredJwtException expiredJwtException) {
            logger.error("Failed to validate token. Token has expired.");
            return null;
        } catch (Exception ex) {
            logger.error("parseAndValidate() ... EXCEPTION" + ex.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", ex);
        }
    }

    private static AuthTokenDetailsDTO createTokenDTOFromClaims(Claims claims) {
        AuthTokenDetailsDTO authTokenDetailsDTO = new AuthTokenDetailsDTO();
        authTokenDetailsDTO.setEmail((String) claims.get("email"));
        authTokenDetailsDTO.setUserId(claims.getSubject());
        authTokenDetailsDTO.setUsername((String) claims.get("username"));

        return authTokenDetailsDTO;
    }

    private static String serializeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

}
