package com.kbalazsworks.stackjudge.unit.domain.services.company_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.Company;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.enums.paginator.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.services.*;
import com.kbalazsworks.stackjudge.domain.services.company_services.SearchService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.integration.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.integration.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.fake_builders.CompanyStatisticFakeBuilder;
import com.kbalazsworks.stackjudge.integration.fake_builders.ReviewFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyServiceSearchTest extends AbstractTest
{
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AddressService addressService;
    @Autowired
    private PaginatorService paginatorService;
    @Autowired
    private JooqService jooqService;
    @Autowired
    private CdnService cdnService;
    @Autowired
    private SearchService searchService;

    @BeforeEach
    @AfterEach
    public void clean()
    {
        companyService.setCompanyRepository(companyRepository);
        companyService.setAddressService(addressService);
        companyService.setPaginatorService(paginatorService);
        companyService.setJooqService(jooqService);
        companyService.setCdnService(cdnService);
        companyService.setSearchService(searchService);
    }

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private record TestData(
        long testedSeekId,
        int testedLimit,
        List<Short> testedRequestRelationIds,
        NavigationEnum testedNavigation,
        List<Company> mockedCompanies,
        Map<Long, CompanyStatistic> mockForGetStatistic,
        List<PaginatorItem> mockForGenerate,
        Map<Long, List<Address>> mockForSearchAddresses,
        Map<Long, List<Review>> mockForReviews,
        CompanySearchServiceResponse expectedResponse
    )
    {
    }

    private TestData provider(int repetition)
    {
        if (repetition == 1)
        {
            return new TestData(
                // tested
                1L,
                2,
                null,
                NavigationEnum.CURRENT_PLUS_1,
                // mock
                new CompanyFakeBuilder().buildAsList(),
                new HashMap<>(),
                new ArrayList<>(),
                new HashMap<>(),
                new HashMap<>(),
                // expected
                new CompanySearchServiceResponse(
                    new CompanyFakeBuilder().buildAsList(),
                    new HashMap<>(),
                    new ArrayList<>(),
                    null,
                    new HashMap<>(),
                    new HashMap<>(),
                    new HashMap<>()
                )
            );
        }
        if (repetition == 2)
        {
            long expectedCompanyId = 164985367L;

            return new TestData(
                // tested
                1L,
                2,
                List.of((short) 1, (short) 2, (short) 3, (short) 4, (short) 5),
                NavigationEnum.CURRENT_PLUS_1,
                // mock
                new CompanyFakeBuilder().buildAsList(),
                Map.of(164985367L, new CompanyStatisticFakeBuilder().build()),
                List.of(new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)),
                Map.of(164985367L, new AddressFakeBuilder().buildAsList()),
                Map.of(164985367L, new ReviewFakeBuilder().buildAsList()),
                // expected
                new CompanySearchServiceResponse(
                    new CompanyFakeBuilder().setId(expectedCompanyId).buildAsList(),
                    new HashMap<>(),
                    List.of(new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)),
                    expectedCompanyId,
                    Map.of(164985367L, new CompanyStatisticFakeBuilder().build()),
                    Map.of(164985367L, new AddressFakeBuilder().buildAsList()),
                    Map.of(164985367L, new ReviewFakeBuilder().buildAsList())
                )
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
    public void allCallableCalled_byTheProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData   testData           = provider(repetitionInfo.getCurrentRepetition());
        List<Long> mockedCompaniesIds = testData.mockedCompanies.stream().map(Company::id).collect(Collectors.toList());

        CompanyRepository companyRepositoryMock = mock(CompanyRepository.class);
        when(companyRepositoryMock.search(testData.testedSeekId, testData.testedNavigation, testData.testedLimit))
            .thenReturn(testData.mockedCompanies);
        when(companyRepositoryMock.countRecordsBeforeId(mockedCompaniesIds.get(0))).thenReturn(1L);
        when(companyRepositoryMock.countRecords()).thenReturn(2L);
        companyService.setCompanyRepository(companyRepositoryMock);

        SearchService searchServiceMock = mock(SearchService.class);
        when(searchServiceMock.getStatistic(mockedCompaniesIds)).thenReturn(testData.mockForGetStatistic);
        companyService.setSearchService(searchServiceMock);

        PaginatorService paginatorServiceMock = mock(PaginatorService.class);
        when(paginatorServiceMock.generate(1L, 2L, testData.testedLimit))
            .thenReturn(testData.mockForGenerate);
        companyService.setPaginatorService(paginatorServiceMock);

        AddressService addressServiceMock = mock(AddressService.class);
        when(addressServiceMock.search(mockedCompaniesIds)).thenReturn(testData.mockForSearchAddresses);
        companyService.setAddressService(addressServiceMock);

        ReviewService reviewServiceMock = mock(ReviewService.class);
        when(reviewServiceMock.search(mockedCompaniesIds)).thenReturn(testData.mockForReviews);
        companyService.setReviewService(reviewServiceMock);

        // Act
        CompanySearchServiceResponse actualResponse = companyService.search(
            testData.testedSeekId,
            testData.testedLimit,
            testData.testedRequestRelationIds,
            testData.testedNavigation
        );

        // Assert
        assertAll(
            () -> assertThat(actualResponse).isEqualTo(testData.expectedResponse)
        );
    }
}
