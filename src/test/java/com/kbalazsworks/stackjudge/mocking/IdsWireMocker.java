package com.kbalazsworks.stackjudge.mocking;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.NonNull;

import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

public class IdsWireMocker
{
    public static void mockGetApiAccountList(@NonNull WireMockServer wireMockServer)
    {
        wireMockServer.stubFor(
            post("/api/account/list")
                .willReturn(
                    ok()
                        .withBody(
                            "{\"data\":{\"extendedUsers\":[{\"id\":\"00000000-0000-0000-0000-000000105001\",\"userName\":\"IDS UserName\",\"normalizedUserName\":\"IDS UserName normalized\",\"email\":\"email@address.com\",\"emailConfirmed\":true,\"profileUrl\":\"http://facebook.com/profile.jpg\"}]},\"success\":true,\"errorCode\":0,\"requestId\":\"1\"}"
                        )
                )
        );
    }

    public static void mockGetWellKnownOpenidConfiguration(@NonNull WireMockServer wireMockServer)
    {
        wireMockServer.stubFor(
            get("/.well-known/openid-configuration")
                .willReturn(
                    ok()
                        .withBody(
                            "{\"issuer\":\"https://localhost:5001\",\"jwks_uri\":\"https://localhost:5001/.well-known/openid-configuration/jwks\",\"authorization_endpoint\":\"https://localhost:5001/connect/authorize\",\"token_endpoint\":\"https://localhost:5001/connect/token\",\"userinfo_endpoint\":\"https://localhost:5001/connect/userinfo\",\"end_session_endpoint\":\"https://localhost:5001/connect/endsession\",\"check_session_iframe\":\"https://localhost:5001/connect/checksession\",\"revocation_endpoint\":\"https://localhost:5001/connect/revocation\",\"introspection_endpoint\":\"https://localhost:5001/connect/introspect\",\"device_authorization_endpoint\":\"https://localhost:5001/connect/deviceauthorization\",\"backchannel_authentication_endpoint\":\"https://localhost:5001/connect/ciba\",\"frontchannel_logout_supported\":true,\"frontchannel_logout_session_supported\":true,\"backchannel_logout_supported\":true,\"backchannel_logout_session_supported\":true,\"scopes_supported\":[\"openid\",\"profile\",\"role\",\"sj\",\"sj.frontend\",\"sj.aws\",\"sj.aws.ec2\",\"sj.aws.ec2.upload_company_logo\",\"sj.aws.ec2.upload_company_map\",\"sj.aws.ses\",\"sj.aws.ses.send_mail\",\"sj.notification\",\"sj.notification.send_push\",\"offline_access\"],\"claims_supported\":[\"sub\",\"name\",\"family_name\",\"given_name\",\"middle_name\",\"nickname\",\"preferred_username\",\"profile\",\"picture\",\"website\",\"gender\",\"birthdate\",\"zoneinfo\",\"locale\",\"updated_at\",\"role\"],\"grant_types_supported\":[\"authorization_code\",\"client_credentials\",\"refresh_token\",\"implicit\",\"password\",\"urn:ietf:params:oauth:grant-type:device_code\",\"urn:openid:params:grant-type:ciba\"],\"response_types_supported\":[\"code\",\"token\",\"id_token\",\"id_token token\",\"code id_token\",\"code token\",\"code id_token token\"],\"response_modes_supported\":[\"form_post\",\"query\",\"fragment\"],\"token_endpoint_auth_methods_supported\":[\"client_secret_basic\",\"client_secret_post\"],\"id_token_signing_alg_values_supported\":[\"RS256\"],\"subject_types_supported\":[\"public\"],\"code_challenge_methods_supported\":[\"plain\",\"S256\"],\"request_parameter_supported\":true,\"request_object_signing_alg_values_supported\":[\"RS256\",\"RS384\",\"RS512\",\"PS256\",\"PS384\",\"PS512\",\"ES256\",\"ES384\",\"ES512\",\"HS256\",\"HS384\",\"HS512\"],\"authorization_response_iss_parameter_supported\":true,\"backchannel_token_delivery_modes_supported\":[\"poll\"],\"backchannel_user_code_parameter_supported\":true}"
                        )
                )
        );
    }

    public static void mockPostConnectToken(@NonNull WireMockServer wireMockServer)
    {
        wireMockServer.stubFor(
            post("/connect/token")
                .willReturn(
                    ok()
                        .withBody(
                            "{\"access_token\":\"eyJhbGciOiJSUzI1NiIsImtpZCI6IkE3MjMwMTUzOUFGODI1RTZBNzZFNzhCMTQ0OEQ1N0MwIiwidHlwIjoiYXQrand0In0.eyJpc3MiOiJodHRwczovL2xvY2FsaG9zdDo1MDAxIiwibmJmIjoxNjY3NTE2MjIzLCJpYXQiOjE2Njc1MTYyMjMsImV4cCI6MTY2NzUzMDYyMywiYXVkIjpbInNqLnJlc291cmNlLmF3cyIsInNqLnJlc291cmNlLmZyb250ZW5kIiwiaHR0cHM6Ly9sb2NhbGhvc3Q6NTAwMS9yZXNvdXJjZXMiXSwic2NvcGUiOlsic2oiLCJzai5hd3MuZWMyLnVwbG9hZF9jb21wYW55X2xvZ28iLCJzai5hd3Muc2VzLnNlbmRfbWFpbCJdLCJjbGllbnRfaWQiOiJzai5hd3MiLCJqdGkiOiI2MkVENDQzQTg0ODM3MTFDRTlGMkVBQ0NBRUI5NTVENiJ9.iLs1R13j0Fhb2STIexEalTIOD9XLFEcOOVczIUxBgj67dQ0m3oR-BJNJZmGe7_HbxuOya5viRFt-5W9MJG6iUVR0H25p12p4eRk-YQNQM8uvsgYPHvDD-xZ6ethcOq7mHwU_T72HCaKAicMpKco_MKA7KRPXgUNRc2jgE7pX6mmrGS7TnLZoCdWCY7zWPPuBbUzZpTZHRn1NhwSyh82wIw_hXfkBSYBR768B8-vWJKCpkWTfuWEmZ1UYkyO7cAeyW6H31yO7I8Ad5toFAMTQnQYYTJHzmW01HCyAnxgm_4EWV1AQ88gq6RNgJ2cuv5ZQ4He9po5nwMxSYf01uWqRWg\",\"expires_in\":14400,\"token_type\":\"Bearer\",\"scope\":\"sj sj.aws.ec2.upload_company_logo sj.aws.ses.send_mail\"}"
                        )
                )
        );
    }
}
