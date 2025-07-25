package io.github.orionlibs.user.authentication;

import io.github.orionlibs.core.cryptology.AESEncryptionKeyProvider;
import io.github.orionlibs.core.cryptology.HmacSha256;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JWTService
{
    private static final long EXPIRATION_MS = 3600000;


    public Key convertSigningKeyToSecretKeyObject(String signingKey)
    {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signingKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }


    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                        .subject(userDetails.getUsername())
                        .claim("authorities", userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                        .signWith(convertSigningKeyToSecretKeyObject(HmacSha256.JWT_SIGNING_KEY), SignatureAlgorithm.HS512)
                        .compact();
    }


    public boolean validateToken(String token, UserDetails userDetails)
    {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public String extractUsername(String token)
    {
        return Jwts.parser()
                        .decryptWith((SecretKey)convertSigningKeyToSecretKeyObject(HmacSha256.JWT_SIGNING_KEY))
                        .build()
                        .parseEncryptedClaims(token)
                        .getBody()
                        .getSubject();
    }


    private boolean isTokenExpired(String token)
    {
        Date exp = Jwts.parser()
                        .decryptWith((SecretKey)convertSigningKeyToSecretKeyObject(HmacSha256.JWT_SIGNING_KEY))
                        .build()
                        .parseEncryptedClaims(token)
                        .getBody()
                        .getExpiration();
        return exp.before(new Date());
    }
}
