package com.kbalazsworks.stackjudge.unit.domain.company_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.address_module.entities.Address;
import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import com.kbalazsworks.stackjudge.domain.company_module.repositories.CompanyRepository;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanySearchServiceResponse;
import com.kbalazsworks.stackjudge.domain.company_module.value_objects.CompanyStatistic;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import com.kbalazsworks.stackjudge.domain.paginator_module.value_objects.PaginatorItem;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.domain.review_module.enums.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.review_module.services.ReviewService;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyStatisticFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.AccountServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.AddressServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.MapsServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.PaginatorServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.SearchServiceMocker;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class CompanyService_searchTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

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
        Map<Long, CompanyAddresses> mockForSearchAddressesAsCompanyAddresses,
        Map<Long, Map<Long, Map<MapPositionEnum, StaticMapResponse>>> mockForAddressMaps,
        Map<Long, Map<Long, List<Review>>> mockForReviews,
        Map<String, IdsUser> mockForUsers,
        Map<Long, List<Long>> mockForOwners,
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
                new HashMap<>(),
                new HashMap<>(),
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
                    new HashMap<>(),
                    new HashMap<>(),
                    new HashMap<>(),
                    new HashMap<>()
                )
            );
        }
        if (repetition == 2)
        {
            return new TestData(
                // tested
                1L,
                2,
                List.of((short) 1, (short) 2, (short) 3, (short) 4, (short) 5),
                NavigationEnum.CURRENT_PLUS_1,
                // mock
                new CompanyFakeBuilder().buildAsList(),
                Map.of(CompanyFakeBuilder.defaultId1, new CompanyStatisticFakeBuilder().build()),
                List.of(new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)),
                Map.of(CompanyFakeBuilder.defaultId1, new AddressFakeBuilder().buildAsList()),
                Map.of(
                    CompanyFakeBuilder.defaultId1,
                    new CompanyAddresses(CompanyFakeBuilder.defaultId1, new AddressFakeBuilder().buildAsList())
                ),
                Map.of(CompanyFakeBuilder.defaultId1, Map.of(AddressFakeBuilder.defaultId1, new HashMap<>())),
                Map.of(
                    CompanyFakeBuilder.defaultId1,
                    Map.of(GroupFakeBuilder.defaultId1, new ReviewFakeBuilder().buildAsList())
                ),
                Map.of(UserFakeBuilder.defaultId1, new IdsUserFakeBuilder().build()),
                new HashMap<>(),
                // expected
                new CompanySearchServiceResponse(
                    new CompanyFakeBuilder().id(CompanyFakeBuilder.defaultId1).buildAsList(),
                    new HashMap<>(),
                    List.of(new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)),
                    CompanyFakeBuilder.defaultId1,
                    Map.of(CompanyFakeBuilder.defaultId1, new CompanyStatisticFakeBuilder().build()),
                    Map.of(
                        CompanyFakeBuilder.defaultId1,
                        new CompanyAddresses(CompanyFakeBuilder.defaultId1, new AddressFakeBuilder().buildAsList())
                    ),
                    Map.of(CompanyFakeBuilder.defaultId1, Map.of(AddressFakeBuilder.defaultId1, new HashMap<>())),
                    Map.of(
                        CompanyFakeBuilder.defaultId1,
                        Map.of(GroupFakeBuilder.defaultId1, new ReviewFakeBuilder().buildAsList())
                    ),
                    new HashMap<>(),
                    Map.of(IdsUserFakeBuilder.defaultId1, new IdsUserFakeBuilder().build())
                )
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @SneakyThrows
    @RepeatedTest(2)
    public void allCallableCalled_byTheProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData   td                 = provider(repetitionInfo.getCurrentRepetition());
        List<Long> mockedCompaniesIds = td.mockedCompanies.stream().map(Company::getId).collect(Collectors.toList());

        CompanyRepository companyRepositoryMock = MockCreator.getCompanyRepositoryMock();
        when(companyRepositoryMock.search(td.testedSeekId, td.testedNavigation, td.testedLimit))
            .thenReturn(td.mockedCompanies);
        when(companyRepositoryMock.countRecordsBeforeId(mockedCompaniesIds.get(0))).thenReturn(1L);
        when(companyRepositoryMock.countRecords()).thenReturn(2L);

        ReviewService reviewServiceMock = MockCreator.getReviewServiceMock();
        when(reviewServiceMock.search(mockedCompaniesIds)).thenReturn(td.mockForReviews);
        when(reviewServiceMock.maskProtectedReviewCreatedBys(td.mockForReviews))
            .thenReturn(td.mockForReviews);

        // Act
        CompanySearchServiceResponse actualResponse = serviceFactory
            .getCompanyService(
                AddressServiceMocker.searchWithCompanyAddresses_returns_addressesMap(
                    mockedCompaniesIds,
                    td.mockForSearchAddressesAsCompanyAddresses
                ),
                SearchServiceMocker.getStatistic_returns_statisticMap(mockedCompaniesIds, td.mockForGetStatistic),
                reviewServiceMock,
                PaginatorServiceMocker.generate_(1L, 2L, td.testedLimit, td.mockForGenerate),
                null,
                AccountServiceMocker.findByUserIdsWithIdMap_returns_mappedUsers(
                    List.of(IdsUserFakeBuilder.defaultId1),
                    td.mockForUsers
                ),
                MapsServiceMocker.searchByAddresses_returns_addressMaps(
                    td.mockForSearchAddresses,
                    td.mockForAddressMaps
                ),
                null,
                null,
                null,
                companyRepositoryMock
            )
            .search(td.testedSeekId, td.testedLimit, td.testedRequestRelationIds, td.testedNavigation);

        // Assert
        assertThat(actualResponse).usingRecursiveComparison().isEqualTo(td.expectedResponse);
    }
}
