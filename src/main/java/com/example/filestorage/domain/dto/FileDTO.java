package com.example.filestorage.domain.dto;

import lombok.Data;

import java.util.Map;

@Data
public class FileDTO {

    private String id;
    private String filename;
    private byte[] bytes;
    private Map<String, String> metadata;
}
