package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.ApiResponseDataIdsServiceAccountListResponse;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsServiceAccountListResponse;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.OpenSdkEmptyPost;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.IdsOpenSdkService;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.common.exceptions.ResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Service
@RequiredArgsConstructor
@Log4j2
public class ListService
{
    private final IdsOpenSdkService idsOpenSdkService;
    private final ObjectMapper      objectMapper = new ObjectMapper().disable(FAIL_ON_UNKNOWN_PROPERTIES);
    private final String            apiUri       = "/api/account/list";

    public StdResponse<IdsServiceAccountListResponse> execute() throws ResponseException
    {
        try
        {
            HttpHeaders headers = new HttpHeaders();

            ResponseEntity<String> response = idsOpenSdkService.post(new OpenSdkEmptyPost(), apiUri, headers);

            ApiResponseDataIdsServiceAccountListResponse body = objectMapper.readValue(
                response.getBody(),
                ApiResponseDataIdsServiceAccountListResponse.class
            );

            return new StdResponse<>(
                response.getStatusCode(),
                response.getHeaders(),
                body.getIdsServiceAccountListResponse()
            );
        }
        catch (Exception e)
        {
            log.error("Api response error: {}", e.getMessage(), e);

            throw new ResponseException("Invalid service response");
        }
    }
}
