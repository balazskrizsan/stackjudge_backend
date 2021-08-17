package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.services.company_service.SearchService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceMocks extends MockCreator
{
    public static SearchService getStatistic_returns_statisticMap(
        List<Long> whenCompaniesIds,
        Map<Long, CompanyStatistic> thanForGetStatistic
    )
    {
        SearchService searchServiceMock = mock(SearchService.class);
        when(searchServiceMock.getStatistic(whenCompaniesIds)).thenReturn(thanForGetStatistic);

        return searchServiceMock;
    }
}
