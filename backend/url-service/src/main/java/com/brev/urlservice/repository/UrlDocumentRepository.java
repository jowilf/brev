package com.brev.urlservice.repository;

import com.brev.urlservice.domain.document.UrlDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.time.OffsetDateTime;

public interface UrlDocumentRepository extends MongoRepository<UrlDocument, String> {

    Page<UrlDocument> findByUserIdOrderByCreatedAtDesc(int userId, Pageable pageable);

    @Query("{'_id':  ?0}")
    @Update("{$set : { 'last_visited' : ?1 } }")
    void updateLastVisited(String id, OffsetDateTime lastVisited);
}
