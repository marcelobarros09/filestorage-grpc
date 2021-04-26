package com.example.filestorage.storage;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.example.filestorage.properties.AzureStorageProperties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Log4j2
@AllArgsConstructor
@Component
public class AzureStorage implements Storage {

    private final AzureStorageProperties azureStorageProperties;

    @Override
    public void upload(String filePath, byte[] bytes, Map<String, String> metadata) {
        BlobContainerClient blobContainerClient = getBlobContainerClient(azureStorageProperties.getContainer());
        log.info(String.format("Upload blob %s in the container: %s", filePath, blobContainerClient.getBlobContainerUrl()));
        BlobClient blobClient = blobContainerClient.getBlobClient(filePath);
        blobClient.upload(new ByteArrayInputStream(bytes), bytes.length);
        blobClient.setMetadata(metadata);
    }

    @Override
    public ByteArrayOutputStream download(String filePath) throws IOException {
        BlobContainerClient blobContainerClient = getBlobContainerClient(azureStorageProperties.getContainer());
        BlobClient blobClient = blobContainerClient.getBlobClient(filePath);
        log.info(String.format("Download the blob: %s", blobClient.getBlobUrl()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        blobClient.download(output);
        output.close();
        return output;
    }

    private BlobServiceClient getBlobServiceClient() {
        var credential = new StorageSharedKeyCredential(azureStorageProperties.getAccountName(),
                azureStorageProperties.getAccountKey());
        return new BlobServiceClientBuilder()
                .endpoint(azureStorageProperties.getEndpoint())
                .credential(credential)
                .buildClient();
    }

    private BlobContainerClient getBlobContainerClient(String containerName) {
        BlobServiceClient blobServiceClient = getBlobServiceClient();
        return blobServiceClient.getBlobContainerClient(containerName);
    }
}
