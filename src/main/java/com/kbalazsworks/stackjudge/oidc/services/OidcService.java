package com.kbalazsworks.stackjudge.oidc.services;

import com.kbalazsworks.stackjudge.oidc.entities.AccessTokenRawResponse;
import com.kbalazsworks.stackjudge.oidc.entities.BasicAuth;
import com.kbalazsworks.stackjudge.oidc.entities.IntrospectRawResponse;
import com.kbalazsworks.stackjudge.oidc.entities.JwksKeyItem;
import com.kbalazsworks.stackjudge.oidc.entities.JwksKeys;
import com.kbalazsworks.stackjudge.oidc.entities.OidcConfig;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcApiException;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcExpiredTokenException;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcJwksVerificationException;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcJwtParseException;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcKeyException;
import com.kbalazsworks.stackjudge.oidc.exceptions.OidcScopeException;
import com.kbalazsworks.stackjudge.oidc.factories.OidcSystemFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
public class OidcService implements IOidcService
{
    private final OidcConfig            oidcConfig;
    private final TokenService          tokenService;
    private final OidcHttpClientService oidcHttpClientService;
    private final OidcSystemFactory     oidcSystemFactory;

    public @NonNull AccessTokenRawResponse callTokenEndpoint(
        @NonNull String clientId,
        @NonNull String clientSecret,
        @NonNull String scope,
        @NonNull String grantType
    )
    throws OidcApiException
    {
        return oidcHttpClientService.post(
            oidcConfig.getTokenEndpoint(),
            new HashMap<>()
            {{
                put("client_id", clientId);
                put("client_secret", clientSecret);
                put("scope", scope);
                put("grant_type", grantType);
            }},
            AccessTokenRawResponse.class
        );
    }

    public @NonNull IntrospectRawResponse callIntrospectEndpoint(
        @NonNull String accessToken,
        @NonNull BasicAuth basicAuth
    ) throws OidcApiException
    {
        return oidcHttpClientService.post(
            oidcConfig.getIntrospectionEndpoint(),
            new HashMap<>()
            {{
                put("token", accessToken);
            }},
            IntrospectRawResponse.class,
            basicAuth
        );
    }

    public @NonNull JwksKeys callJwksEndpoint() throws OidcApiException
    {
        return oidcHttpClientService.get(oidcConfig.getJwksUri(), JwksKeys.class);
    }

    public void checkScopesInToken(@NonNull String token, @NonNull List<String> scopes)
    throws OidcScopeException, OidcJwtParseException, OidcExpiredTokenException, OidcJwksVerificationException
    {
        checkValidated(token);

        List<String> matchedScopes = tokenService
            .getJwtData(token)
            .getScope()
            .stream()
            .filter(scopes::contains)
            .collect(Collectors.toList());

        if (matchedScopes.isEmpty())
        {
            throw new OidcScopeException("Scope missing from token");
        }
    }

    public void checkValidated(String token)
    throws OidcExpiredTokenException, OidcJwksVerificationException, OidcJwtParseException
    {
        checkExpiredToken(token);
        checkJwksVerifiedToken(token);
    }

    public void checkExpiredToken(@NonNull String token) throws OidcExpiredTokenException, OidcJwtParseException
    {
        if (isExpiredToken(token))
        {
            throw new OidcExpiredTokenException();
        }
    }

    public @NonNull Boolean isExpiredToken(@NonNull String token) throws OidcJwtParseException
    {
        Integer expiration = tokenService.getJwtData(token).getExp();
        long    now        = oidcSystemFactory.getCurrentTimeMillis() / 1000;

        return expiration < now;
    }

    public void checkJwksVerifiedToken(@NonNull String token) throws OidcJwksVerificationException
    {
        Boolean isVerified;
        try
        {
            isVerified = checkJwksVerifiedTokenLogic(token);
        }
        catch (Exception e)
        {
            throw new OidcJwksVerificationException(e.getMessage());
        }

        if (!isVerified)
        {
            throw new OidcJwksVerificationException();
        }
    }

    private @NonNull Boolean checkJwksVerifiedTokenLogic(@NonNull String token)
    throws OidcApiException, OidcJwtParseException, OidcKeyException
    {
        String alg = tokenService.getJwtHeader(token).alg;

        final JwksKeyItem key = callJwksEndpoint()
            .getKeys()
            .stream()
            .filter(k -> k.getAlg().equals(alg))
            .findFirst()
            .get();

        PublicKey publicKey  = tokenService.getPublicKey(key.getN(), key.getE());
        byte[]    signature  = tokenService.getSignature(token);
        val       signedData = tokenService.getSignedData(token);

        return tokenService.isVerified(publicKey, signedData, signature);
    }

    public @NonNull Boolean isJwksVerifiedToken(@NonNull String token)
    {
        try
        {
            return checkJwksVerifiedTokenLogic(token);
        }
        catch (Exception e)
        {
            log.error("JWKS verification failed: {}", e.getMessage());

            return false;
        }
    }
}
