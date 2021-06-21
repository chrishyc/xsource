package web.oauth2.jwt;



import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class HS256Jwt {
    
    
    public static void main(String[] args) {
        String sharedTokenSecret = "hellooauthhellooauthhellooauthhellooauth";
        Key key = new SecretKeySpec(sharedTokenSecret.getBytes(),
                SignatureAlgorithm.HS256.getJcaName());
        
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");
        
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("iss", "http://localhost:8081/");
        payloadMap.put("sub", "XIAOMINGTEST");
        payloadMap.put("aud", "APPID_RABBIT");
        payloadMap.put("exp", 1584105790703L);
        payloadMap.put("iat", 1584105948372L);
        
        String jws2 = Jwts.builder().setHeaderParams(headerMap).setClaims(payloadMap).signWith(key, SignatureAlgorithm.HS256).compact();
        
        System.out.println("jws2:" + jws2);

        /*String sharedTokenSecret2="hellooauthhellooauthhellooauthhellooaut0";
        Key key2 = new SecretKeySpec(sharedTokenSecret2.getBytes(),
                SignatureAlgorithm.HS256.getJcaName());

        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key2).build().parseClaimsJws(jws2);*/
        
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws2);
        
        JwsHeader header = claimsJws.getHeader();
        Claims body = claimsJws.getBody();
        
        System.out.println("jwt header:" + header);
        System.out.println("jwt body:" + body);
        System.out.println("jwt sub:" + body.getSubject());
        System.out.println("jwt aud:" + body.getAudience());
        System.out.println("jwt iss:" + body.getIssuer());
        System.out.println("jwt exp:" + body.getExpiration());
        System.out.println("jwt iat:" + body.getIssuedAt());
    }
    
}
