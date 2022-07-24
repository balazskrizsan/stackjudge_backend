package com.kbalazsworks.stackjudge.oidc.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.oidc.entities.JwtData;
import com.kbalazsworks.stackjudge.oidc.entities.JwtHeader;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcJwtParseException;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcKeyException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Service
@Log4j2
public class TokenService
{
    private static ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public JwtData getJwtData(String token) throws OidcJwtParseException
    {
        try
        {
            String[] tokenParts     = token.split("\\.");
            byte[]   dataPart       = tokenParts[1].getBytes();
            byte[]   decodedJwtData = Base64.getDecoder().decode(dataPart);

            return objectMapper.readValue(decodedJwtData, JwtData.class);
        }
        catch (Exception e)
        {
            throw new OidcJwtParseException(e.getMessage());
        }
    }

    public JwtHeader getJwtHeader(String token) throws OidcJwtParseException
    {
        try
        {
            String[] tokenParts       = token.split("\\.");
            byte[]   dataPart         = tokenParts[0].getBytes();
            byte[]   decodedJwtHeader = Base64.getDecoder().decode(dataPart);

            return objectMapper.readValue(decodedJwtHeader, JwtHeader.class);
        }
        catch (Exception e)
        {
            throw new OidcJwtParseException(e.getMessage());
        }
    }

    public PublicKey getPublicKey(String modulus, String exponent) throws OidcKeyException
    {
        try
        {
            var exponentB   = Base64.getUrlDecoder().decode(exponent);
            var modulusB    = Base64.getUrlDecoder().decode(modulus);
            var bigExponent = new BigInteger(1, exponentB);
            var bigModulus  = new BigInteger(1, modulusB);
            var publicKey = KeyFactory
                .getInstance("RSA")
                .generatePublic(new RSAPublicKeySpec(bigModulus, bigExponent));

            return publicKey;
        }
        catch (IndexOutOfBoundsException | InvalidKeySpecException | NoSuchAlgorithmException e)
        {
            log.error("Public key error: {}", e.getMessage());

            throw new OidcKeyException("Public key error");
        }
    }

    public byte[] getSignature(String token) throws OidcJwtParseException
    {
        try
        {
            String signatureB64u = token.substring(token.lastIndexOf(".") + 1);

            return Base64.getUrlDecoder().decode(signatureB64u);
        }
        catch (Exception e)
        {
            throw new OidcJwtParseException(e.getMessage());
        }
    }

    public byte[] getSignedData(String token) throws OidcJwtParseException
    {
        try
        {
            return token.substring(0, token.lastIndexOf(".")).getBytes();
        }
        catch (Exception e)
        {
            throw new OidcJwtParseException(e.getMessage());
        }
    }

    public Boolean isVerified(PublicKey publicKey, byte[] signedData, byte[] signature)
    {
        try
        {
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);
            sig.update(signedData);

            return sig.verify(signature);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e)
        {
            log.error("Publick key verification error: {}", e.getMessage());

            return false;
        }
    }
}
