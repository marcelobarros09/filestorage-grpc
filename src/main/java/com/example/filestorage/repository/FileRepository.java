package com.example.filestorage.repository;

import com.example.filestorage.domain.model.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {
}
