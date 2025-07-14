package io.github.orionlibs.documents.document.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer>
{
    /**
     * Finds documents by type
     * @param type The type of document
     * @return The documents of given type
     */
    List<DocumentEntity> findAllByType(DocumentType type);
}
