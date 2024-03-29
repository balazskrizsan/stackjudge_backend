package com.kbalazsworks.stackjudge.integration.domain.map_module.services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMap;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.GoogleStaticMapMarker;
import com.kbalazsworks.stackjudge.domain.map_module.value_objects.StaticMapResponse;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapMarkerFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.PostUploadRequestFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.StdResponseCdnServicePutResponseFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.StdResponsePutAndSaveResponseFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.setup_mock.S3UploadApiServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.StaticProxyServiceMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.UrlFactoryMocker;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostUploadRequest;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PutAndSaveResponse;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static com.kbalazsworks.stackjudge.domain.map_module.enums.MapPositionEnum.COMPANY_LEFT;
import static com.kbalazsworks.stackjudge.fake_builders.GoogleMapsUrlWithHashFakeBuilder.fakeGoogleMapsUrl;
import static com.kbalazsworks.stackjudge.fake_builders.GoogleStaticMapsCacheFakeBuilder.fileName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class MapsService_staticProxyTest extends AbstractIntegrationTest
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
        GoogleStaticMap testedMap = new GoogleStaticMapFakeBuilder().build();
        List<GoogleStaticMapMarker> testedMapMarker = new GoogleStaticMapMarkerFakeBuilder()
            .buildAsListWithTwoItems();
        MapPositionEnum testedMapPositionEnum = COMPANY_LEFT;

        StaticMapResponse expectedStaticMapResponse = new StaticMapResponse(fileName, COMPANY_LEFT);

        // Act
        StaticMapResponse actual = serviceFactory
            .getMapsService(
                null,
                StaticProxyServiceMocker.generateMapUrl_returns_GoogleMapsUrlWithHash(testedMap, testedMapMarker),
                null,
                null,
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
        GoogleStaticMap testedMap             = new GoogleStaticMapFakeBuilder().build();
        MapPositionEnum testedMapPositionEnum = COMPANY_LEFT;
        List<GoogleStaticMapMarker> testedMapMarker = new GoogleStaticMapMarkerFakeBuilder()
            .buildAsListWithTwoItems();
        PostUploadRequest                  mockAwsRequest  = new PostUploadRequestFakeBuilder().build();
        StdResponse<PutAndSaveResponse> mockAwsResponse = new StdResponsePutAndSaveResponseFakeBuilder().build();

        StaticMapResponse expectedStaticMapResponse = new StaticMapResponse(fakeGoogleMapsUrl, COMPANY_LEFT);

        // Act
        StaticMapResponse actual = serviceFactory
            .getMapsService(
                null,
                StaticProxyServiceMocker.generateMapUrl_returns_GoogleMapsUrlWithHash(testedMap, testedMapMarker),
                null,
                null,
                null,
                S3UploadApiServiceMocker.execute_returns(mockAwsRequest, mockAwsResponse),
                UrlFactoryMocker.create_returns_URL(fakeGoogleMapsUrl)
            )
            .staticProxy(testedMap, testedMapMarker, testedMapPositionEnum);

        // Assert
        assertThat(actual).isEqualTo(expectedStaticMapResponse);
    }
}
