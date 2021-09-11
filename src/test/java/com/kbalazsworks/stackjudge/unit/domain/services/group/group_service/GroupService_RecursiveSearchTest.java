package com.kbalazsworks.stackjudge.unit.domain.services.group.group_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.RecursiveGroupFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class GroupService_RecursiveSearchTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void LongParam_callsListOfLongParamGeneric()
    {
        // Arrange
        long testedCompanyId = CompanyFakeBuilder.defaultId1;

        GroupService groupServicePartialMock = spy(serviceFactory.getGroupService());
        doReturn(List.of(new RecursiveGroupFakeBuilder().build()))
            .when(groupServicePartialMock)
            .recursiveSearch(List.of(CompanyFakeBuilder.defaultId1));

        List<RecursiveGroup> expectedGroups = new RecursiveGroupFakeBuilder().buildAsList();

        // Act
        List<RecursiveGroup> actualGroups = groupServicePartialMock.recursiveSearch(testedCompanyId);

        // Assert
        assertThat(actualGroups).isEqualTo(expectedGroups);
    }
}
