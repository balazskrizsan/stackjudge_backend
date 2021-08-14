package com.kbalazsworks.stackjudge.integration.domain.services.map_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.value_objects.maps_service.StaticMapResponse;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapMarkerFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.setup_mock.CdnServiceMocks;
import com.kbalazsworks.stackjudge.mocking.setup_mock.StaticProxyServiceMocks;
import com.kbalazsworks.stackjudge.mocking.setup_mock.UrlFactoryMocks;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static com.kbalazsworks.stackjudge.domain.enums.aws.CdnNamespaceEnum.STATIC_MAPS;
import static com.kbalazsworks.stackjudge.domain.enums.google_maps.MapPositionEnum.COMPANY_LEFT;
import static com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder.fakeGoogleMapsUrl;
import static com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder.fakeUrlHash;
import static com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapsCacheFakeBuilder.fileName;
import static org.assertj.core.api.Assertions.assertThat;
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
    @SneakyThrows
    public void selectingWithAlreadyCachedMapInfo_returnsTheSqlCachedS3Ul()
    {
        // Arrange
        GoogleStaticMap             testedMap             = GoogleStaticMapFakeBuilder.build();
        List<GoogleStaticMapMarker> testedMapMarker       = GoogleStaticMapMarkerFakeBuilder.buildAsListWithTwoItems();
        MapPositionEnum             testedMapPositionEnum = COMPANY_LEFT;

        StaticMapResponse expectedStaticMapResponse = new StaticMapResponse(fileName, COMPANY_LEFT);

        // Act
        StaticMapResponse actual = serviceFactory.getMapsService(
            null,
            null,
            StaticProxyServiceMocks.generateMapUrl_returns_GoogleMapsUrlWithHash(testedMap, testedMapMarker),
            null,
            null,
            null
        )
            .staticProxy(testedMap, testedMapMarker, testedMapPositionEnum);

        // Assert
        assertThat(actual).isEqualTo(expectedStaticMapResponse);
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @SneakyThrows
    public void selectingNonExistingCachedMapInfo_loadGoogleMapSaveToS3AndResponseWithS3Url()
    {
        // Arrange
        GoogleStaticMap             testedMap             = GoogleStaticMapFakeBuilder.build();
        List<GoogleStaticMapMarker> testedMapMarker       = GoogleStaticMapMarkerFakeBuilder.buildAsListWithTwoItems();
        MapPositionEnum             testedMapPositionEnum = COMPANY_LEFT;

        StaticMapResponse expectedStaticMapResponse = new StaticMapResponse(fakeGoogleMapsUrl, COMPANY_LEFT);

        // Act
        StaticMapResponse actual = serviceFactory.getMapsService(
            null,
            CdnServiceMocks.put_returns_CdnServicePutResponse(STATIC_MAPS, fakeUrlHash, "jpg", ""),
            StaticProxyServiceMocks.generateMapUrl_returns_GoogleMapsUrlWithHash(testedMap, testedMapMarker),
            null,
            null,
            UrlFactoryMocks.create_returns_URL(fakeGoogleMapsUrl)
        )
            .staticProxy(testedMap, testedMapMarker, testedMapPositionEnum);

        // Assert
        assertThat(actual).isEqualTo(expectedStaticMapResponse);
    }
}
