package com.example.filestorage.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "files")
public class File {

    @Id
    private String id;
    private String filename;
    private String remoteFilename;
    private String filePath;
    private Map<String, String> metadata;
}
