package com.example.filestorage.service;

import com.example.filestorage.domain.dto.FileDTO;
import com.example.filestorage.domain.model.File;
import com.example.filestorage.properties.FileStorageProperties;
import com.example.filestorage.repository.FileRepository;
import com.example.filestorage.storage.Storage;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
@Service
public class FileService {

    private final Storage fileStorage;
    private final FileRepository fileRepository;
    private final FileStorageProperties fileStorageProperties;

    public String upload(FileDTO fileDTO) {
        String id = generateId();
        String remoteFileName = generateRemoteFilename(id, fileDTO.getFilename());
        String filePath = fileStorageProperties.getBaseDirectory().concat(remoteFileName);

        fileStorage.upload(filePath, fileDTO.getBytes(), fileDTO.getMetadata());

        File file = new File();
        file.setId(id);
        file.setFilename(fileDTO.getFilename());
        file.setMetadata(fileDTO.getMetadata());
        file.setRemoteFilename(remoteFileName);
        file.setFilePath(filePath);

        fileRepository.save(file);

        return id;
    }

    public FileDTO download(String id) throws IOException {
        File file = fileRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        ByteArrayOutputStream outputStream = fileStorage.download(file.getFilePath());

        FileDTO fileDTO = new FileDTO();
        fileDTO.setId(file.getId());
        fileDTO.setFilename(file.getFilename());
        fileDTO.setBytes(outputStream.toByteArray());
        fileDTO.setMetadata(file.getMetadata());

        return fileDTO;
    }

    private String generateRemoteFilename(String id, String filename){
        return id.concat("_").concat(filename);
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
