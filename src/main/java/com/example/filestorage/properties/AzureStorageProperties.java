package com.example.filestorage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("azure.storage")
public class AzureStorageProperties {

    private String container;
    private String endpoint;
    private String accountName;
    private String accountKey;

}
