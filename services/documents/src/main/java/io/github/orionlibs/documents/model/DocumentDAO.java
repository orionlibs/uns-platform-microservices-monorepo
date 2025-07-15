package io.github.orionlibs.documents.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentDAO extends JpaRepository<DocumentModel, Integer>
{
    /**
     * Finds documents by type
     * @param type The type of document
     * @return The documents of given type
     */
    List<DocumentModel> findAllByType(DocumentType type);
}
