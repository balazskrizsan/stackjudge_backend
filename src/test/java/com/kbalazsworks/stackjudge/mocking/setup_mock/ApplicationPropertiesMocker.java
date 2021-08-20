package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;

import static org.mockito.Mockito.when;

public class ApplicationPropertiesMocker extends MockCreator
{
    public static final String AWS_ACCESS_KEY    = "aws-access-key";
    public static final String AWS_SECRET_KEY    = "aws-secret-key";
    public static final String AWS_S3_CDN_BUCKET = "aws-s3-cdn-bucket";
    public static final String GOOGLE_MAPS_KEY   = "aws-s3-cdn-bucket";
    public static final String JWT_SECRET        = "12345678901234567890123456789012";
    public static final String SITE_DOMAIN       = "dev.stackjudge.com";

    public static ApplicationProperties getDefaultMock()
    {
        ApplicationProperties mock = getApplicationPropertiesMock();

        when(mock.getAwsAccessKey()).thenReturn(AWS_ACCESS_KEY);
        when(mock.getAwsSecretKey()).thenReturn(AWS_SECRET_KEY);
        when(mock.getAwsS3CdnBucket()).thenReturn(AWS_S3_CDN_BUCKET);
        when(mock.getGoogleMapsKey()).thenReturn(GOOGLE_MAPS_KEY);
        when(mock.getJwtSecret()).thenReturn(JWT_SECRET);
        when(mock.getSiteDomain()).thenReturn(SITE_DOMAIN);

        return mock;
    }
}