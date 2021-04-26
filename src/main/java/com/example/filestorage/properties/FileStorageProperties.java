package com.example.filestorage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("filestorage")
public class FileStorageProperties {

    private String baseDirectory;
}
