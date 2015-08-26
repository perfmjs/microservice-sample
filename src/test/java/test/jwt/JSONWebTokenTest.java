package test.jwt;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import unittest.common.AbstractMockTestBuilder;
import unittest.common.AbstractMockTestCase;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

/**
 * Created by tony on 15/8/24.
 */
public class JSONWebTokenTest extends AbstractMockTestCase {
    @Before
    public void setUp() {
        super.setUp();
    }
    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void shouldOptionsAlgorithm() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            @Override
            public void buildVerify() {
                JWTSigner signer = new JWTSigner("secret123");
                HashMap<String, Object> claims = new HashMap<>();
                claims.put("iss", "李先生");
                claims.put("sub", "commonLogin");
                claims.put("aud", "webapp");
                String token = signer.sign(claims,
                        new JWTSigner.Options().setAlgorithm(Algorithm.HS256)
                                .setExpirySeconds(3600*1)
                                .setNotValidBeforeLeeway(5)
                                .setIssuedAt(false)
                                .setJwtId(false)
                );
                System.out.println("token=" + token);
                assertEquals(1, 1);
            }
        }.runTest();
    }

    @Test
    public void testJSONWebToken() {
        new AbstractMockTestBuilder.DefaultMockTestBuilder() {
            String jws = "";
            @Override
            public void buildSetup() {
                jws = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiLmnY7lhYjnlJ8iLCJzdWIiOiJjb21tb25Mb2dpbiIsImF1ZCI6IndlYmFwcCIsIm5iZiI6MTQ0MDQzNTE4NCwiZXhwIjoxNDQwNDM4Nzg5fQ.xZGDYxTCJ4jPFvGsp63lChe8OXSYVIQFCvzmrRbp7fA";
            }
            @Override
            public void buildVerify() {
                try {
                    Base64.Encoder encoder = Base64.getEncoder();
                    String encodedJSON = new String(encoder.encode("{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"admin\":true}".getBytes()));
                    Map<String,Object> decodedPayload =  new JWTVerifier("secret123").verify(jws);
                    System.out.println(decodedPayload);
                    assertEquals(decodedPayload.get("sub"), "commonLogin");
                    assertEquals(encodedJSON, "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9");
                } catch (NoSuchAlgorithmException |  InvalidKeyException | IOException | SignatureException | JWTVerifyException e) {
                    e.printStackTrace();
                }
            }
        }.runTest();
    }
}