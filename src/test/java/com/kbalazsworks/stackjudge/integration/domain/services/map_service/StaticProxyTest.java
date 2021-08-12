package com.kbalazsworks.stackjudge.integration.domain.services.map_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.exceptions.ContentReadException;
import com.kbalazsworks.stackjudge.domain.services.map_service.StaticProxyService;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapMarkerFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapsCacheFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class StaticProxyTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_google_static_maps_cache.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void selectingWithAlreadyCachedMapInfo_returnsTheSqlCachedS3Ul() throws ContentReadException
    {
        // Arrange
        GoogleStaticMap testedGoogleStaticMap = GoogleStaticMapFakeBuilder.build();
        List<GoogleStaticMapMarker> testedGoogleStaticMapMarker
            = GoogleStaticMapMarkerFakeBuilder.buildAsListWithTwoItems();
        MapPositionEnum testedMapPositionEnum = MapPositionEnum.COMPANY_LEFT;

        StaticProxyService staticProxyServiceMock = mock(StaticProxyService.class);
        when(staticProxyServiceMock.generateMapUrl(testedGoogleStaticMap, testedGoogleStaticMapMarker))
            .thenReturn(GoogleMapsUrlWithHashFakeBuilder.build());

        StaticMapResponse expectedStaticMapResponse = new StaticMapResponse(
            GoogleStaticMapsCacheFakeBuilder.fileName,
            MapPositionEnum.COMPANY_LEFT
        );

        // Act
        StaticMapResponse actual = serviceFactory
            .getMapsService(null, null, staticProxyServiceMock, null, null)
            .staticProxy(
                testedGoogleStaticMap,
                testedGoogleStaticMapMarker,
                testedMapPositionEnum
            );

        // Assert
        assertThat(actual).isEqualTo(expectedStaticMapResponse);
    }
}
