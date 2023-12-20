package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.requests.company_request.SearchRequest;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyService;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController("CompanySearchAction")
@RequestMapping(CompanyConfig.CONTROLLER_URI)
@RequiredArgsConstructor
@Slf4j
public class SearchAction
{
    private final CompanyService companyService;

    @ApiResponses(
        {
            @ApiResponse(
                responseCode = "200",
                description = "It's 200 OK",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = CompanySearchServiceResponse.class)
                    )}
            ),
            @ApiResponse(
                responseCode = "600",
                description = "Dummy response for extra schema files",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                            oneOf = {
                                OpenApiCustomSchemas.CompanyGroupsMap.class,
                                OpenApiCustomSchemas.CompanyStatisticsMap.class,
                                OpenApiCustomSchemas.CompanyAddressesMap.class,
                                OpenApiCustomSchemas.companyAddressMapsMap.class,
                                OpenApiCustomSchemas.CompanyAddressesMapsByMapIdMap.class,
                                OpenApiCustomSchemas.CompanyAddressMapsStaticMapResponseMap.class,
                                OpenApiCustomSchemas.CompanyReviewsMap.class,
                                OpenApiCustomSchemas.ReviewsMap.class,
                                OpenApiCustomSchemas.CompanyOwnersMap.class,
                                OpenApiCustomSchemas.CompanyUsersMap.class,
                            }
                        )
                    )}
            )
        }
    )
    @GetMapping
    public ResponseEntity<ResponseData<CompanySearchServiceResponse>> action(
        @RequestParam Integer seekId,
        @RequestParam Short limit,
        @RequestParam List<Short> requestRelationIds,
        @RequestParam Optional<Short> navigationId
    )
    throws Exception
    {
        SearchRequest request = new SearchRequest(seekId, limit, requestRelationIds, navigationId.orElse(null));

        log.info("Request: {}", request);

        Short navigation = request.navigationId();
        CompanySearchServiceResponse response = companyService.search(
            request.seekId(),
            request.limit(),
            request.requestRelationIds(),
            navigation == null ? null : NavigationEnum.getByValue(navigation)
        );

        return new ResponseEntityBuilder<CompanySearchServiceResponse>().data(response).build();
    }
}
