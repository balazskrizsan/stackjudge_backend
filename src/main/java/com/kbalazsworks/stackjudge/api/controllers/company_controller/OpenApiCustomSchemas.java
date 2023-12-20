package com.kbalazsworks.stackjudge.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwners;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.group_module.value_objects.RecursiveGroupTree;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;

import java.util.List;

public class OpenApiCustomSchemas
{
    public static class CompanyGroupsMap
    {
        public String                   key;
        public List<RecursiveGroupTree> value;
    }

    public static class CompanyStatisticsMap
    {
        public String           key;
        public CompanyStatistic value;
    }

    public static class CompanyAddressesMap
    {
        public String           key;
        public CompanyAddresses value;
    }

    public static class companyAddressMapsMap
    {
        public Long                                 key; // companyId
        public List<CompanyAddressesMapsByMapIdMap> value;
    }

    public static class CompanyAddressesMapsByMapIdMap
    {
        public Long                                         key; // mapId
        public List<CompanyAddressMapsStaticMapResponseMap> value;
    }

    public static class CompanyAddressMapsStaticMapResponseMap
    {
        public MapPositionEnum   key; // positionEnumId
        public StaticMapResponse value;
    }

    public static class CompanyReviewsMap
    {
        public Long             key;
        public List<ReviewsMap> value;
    }

    public static class ReviewsMap
    {
        public Long         key;
        public List<Review> value;
    }

    public static class CompanyOwnersMap
    {
        public Long          key;
        public CompanyOwners value;
    }

    public static class CompanyUsersMap
    {
        public String  key;
        public IdsUser value;
    }
}
