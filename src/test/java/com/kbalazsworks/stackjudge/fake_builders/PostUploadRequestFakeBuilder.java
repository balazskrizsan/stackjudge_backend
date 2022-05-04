package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.aws_module.enums.CdnNamespaceEnum;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.OpenSdkFileService;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PostUploadRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;

@Accessors(fluent = true)
@Getter
@Setter
public class PostUploadRequestFakeBuilder
{
    private String                        cdnNamespace  = CdnNamespaceEnum.STATIC_MAPS.name();
    private String                        subFolder     = "";
    private String                        fileName      = GoogleStaticMapsCacheFakeBuilder.hash;
    private String                        fileExtension = "jpg";
    private HttpEntity<ByteArrayResource> content       = new OpenSdkFileService()
        .createByteArrayResourceEntityFromString(
            "content".getBytes(),
            "hash_value.jpg"
        );

    public PostUploadRequest build()
    {
        return new PostUploadRequest(cdnNamespace, subFolder, fileName, fileExtension, content);
    }
}
