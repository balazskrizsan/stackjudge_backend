package com.kbalazsworks.stackjudge.domain.company_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroupTree;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import com.kbalazsworks.stackjudge.domain.paginator_module.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

public record CompanySearchServiceResponse(
    @JsonProperty
    List<Company> companies,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/CompanyGroupsMap"))
    @JsonProperty
    Map<Long, List<RecursiveGroupTree>> companyGroups,
    @JsonProperty
    List<PaginatorItem> paginator,
    @JsonProperty
    Long newSeekId,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/CompanyStatisticsMap"))
    @JsonProperty
    Map<Long, CompanyStatistic> companyStatistics,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/CompanyAddressesMap"))
    @JsonProperty
    Map<Long, CompanyAddresses> companyAddresses,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/companyAddressMapsMap"))
    @JsonProperty
    Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> companyAddressMaps,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/CompanyReviewsMap"))
    @JsonProperty
    Map<Long, Map<Long, List<Review>>> companyReviews,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/CompanyOwnersMap"))
    @JsonProperty
    Map<Long, CompanyOwners> companyOwners,
    @ArraySchema(schema = @Schema(ref = "#/components/schemas/CompanyUsersMap"))
    @JsonProperty
    Map<String, IdsUser> companyUsers
)
{
}
