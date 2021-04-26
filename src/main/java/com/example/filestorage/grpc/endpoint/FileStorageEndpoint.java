package com.example.filestorage.grpc.endpoint;

import com.example.filestorage.domain.dto.FileDTO;
import com.example.filestorage.grpc.service.*;
import com.example.filestorage.service.FileService;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class FileStorageEndpoint extends FileStorageServiceGrpc.FileStorageServiceImplBase {

    private final FileService fileService;

    @Override
    public void upload(FileStorageUploadRequest request, StreamObserver<FileStorageUploadResponse> responseObserver) {
        long startTime = System.currentTimeMillis();
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(request.getFilename());
        fileDTO.setBytes(request.getBytes().toByteArray());
        fileDTO.setMetadata(request.getMetadataMap());

        try {
            String fileId = fileService.upload(fileDTO);
            responseObserver.onNext(FileStorageUploadResponse.newBuilder()
                                        .setFileId(fileId)
                                        .setSuccess(true)
                                        .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error(e);
            responseObserver.onNext(FileStorageUploadResponse.newBuilder()
                                        .setSuccess(false)
                                        .setErrorMessage(e.getMessage())
                                        .build());
            responseObserver.onError(e);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info(String.format("Upload Execution Time: %s milliseconds", executionTime));
    }

    @Override
    public void download(FileStorageDownloadRequest request, StreamObserver<FileStorageDownloadResponse> responseObserver) {
        long startTime = System.currentTimeMillis();
        try {
            FileDTO fileDTO = fileService.download(request.getFileId());
            responseObserver.onNext(FileStorageDownloadResponse.newBuilder()
                                        .setFileId(fileDTO.getId())
                                        .setBytes(ByteString.copyFrom(fileDTO.getBytes()))
                                        .setFilename(fileDTO.getFilename())
                                        .putAllMetadata(fileDTO.getMetadata())
                                        .setSuccess(true)
                                        .build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error(e);
            responseObserver.onNext(FileStorageDownloadResponse.newBuilder()
                                        .setSuccess(false)
                                        .setErrorMessage(e.getMessage())
                                        .build());
            responseObserver.onError(e);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info(String.format("Download Execution Time: %s milliseconds", executionTime));
    }
}
