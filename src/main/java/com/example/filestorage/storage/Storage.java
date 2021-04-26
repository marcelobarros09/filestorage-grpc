package com.example.filestorage.storage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public interface Storage {

    void upload(String filePath, byte[] bytes, Map<String, String> metadata);

    ByteArrayOutputStream download(String filePath) throws IOException;
}
