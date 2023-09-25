package com.brev.urlservice.repository;

import com.brev.urlservice.domain.document.UrlDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlDocumentRepository extends MongoRepository<UrlDocument, String> {

    Page<UrlDocument> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);
}
