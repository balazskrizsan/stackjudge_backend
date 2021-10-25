package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.company_module.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class SearchServiceMocker extends MockCreator
{
    public static SearchService getStatistic_returns_statisticMap(
        List<Long> whenCompaniesIds,
        Map<Long, CompanyStatistic> thanForGetStatistic
    )
    {
        SearchService mock = getSearchServiceMock();
        when(mock.getStatistic(whenCompaniesIds)).thenReturn(thanForGetStatistic);

        return mock;
    }
}
