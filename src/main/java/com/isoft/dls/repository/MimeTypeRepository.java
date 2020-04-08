package com.isoft.dls.repository;

import com.isoft.dls.domain.MimeType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the MimeType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MimeTypeRepository extends JpaRepository<MimeType, Long>, JpaSpecificationExecutor<MimeType> {

    enum MimeTypeCache {
        GET_ALL_MIME_TYPES_CACHE;
        public static final String GET_MIME_TYPE_BY_EXTENSION = "getByExtension";
    }

    /**
     * Get the "extension" Mime Type.
     *
     * @param extension the code of the entity
     * @return the entity
     */
    Optional<MimeType> getByExtension(String extension);
}
