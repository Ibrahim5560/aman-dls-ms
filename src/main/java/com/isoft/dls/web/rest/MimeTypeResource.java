package com.isoft.dls.web.rest;

import com.isoft.dls.service.MimeTypeService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.MimeTypeDTO;
import com.isoft.dls.service.dto.MimeTypeCriteria;
import com.isoft.dls.service.MimeTypeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.isoft.dls.domain.MimeType}.
 */
@RestController
@RequestMapping("/api")
public class MimeTypeResource {

    private final Logger log = LoggerFactory.getLogger(MimeTypeResource.class);

    private static final String ENTITY_NAME = "amanDlsmsMimeType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MimeTypeService mimeTypeService;

    private final MimeTypeQueryService mimeTypeQueryService;

    public MimeTypeResource(MimeTypeService mimeTypeService, MimeTypeQueryService mimeTypeQueryService) {
        this.mimeTypeService = mimeTypeService;
        this.mimeTypeQueryService = mimeTypeQueryService;
    }

    /**
     * {@code POST  /mime-types} : Create a new mimeType.
     *
     * @param mimeTypeDTO the mimeTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mimeTypeDTO, or with status {@code 400 (Bad Request)} if the mimeType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mime-types")
    public ResponseEntity<MimeTypeDTO> createMimeType(@Valid @RequestBody MimeTypeDTO mimeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save MimeType : {}", mimeTypeDTO);
        if (mimeTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new mimeType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MimeTypeDTO result = mimeTypeService.save(mimeTypeDTO);
        return ResponseEntity.created(new URI("/api/mime-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mime-types} : Updates an existing mimeType.
     *
     * @param mimeTypeDTO the mimeTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mimeTypeDTO,
     * or with status {@code 400 (Bad Request)} if the mimeTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mimeTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mime-types")
    public ResponseEntity<MimeTypeDTO> updateMimeType(@Valid @RequestBody MimeTypeDTO mimeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update MimeType : {}", mimeTypeDTO);
        if (mimeTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MimeTypeDTO result = mimeTypeService.save(mimeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mimeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mime-types} : get all the mimeTypes.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mimeTypes in body.
     */
    @GetMapping("/mime-types")
    public ResponseEntity<List<MimeTypeDTO>> getAllMimeTypes(MimeTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MimeTypes by criteria: {}", criteria);
        Page<MimeTypeDTO> page = mimeTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /mime-types/count} : count all the mimeTypes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/mime-types/count")
    public ResponseEntity<Long> countMimeTypes(MimeTypeCriteria criteria) {
        log.debug("REST request to count MimeTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(mimeTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /mime-types/:id} : get the "id" mimeType.
     *
     * @param id the id of the mimeTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mimeTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mime-types/{id}")
    public ResponseEntity<MimeTypeDTO> getMimeType(@PathVariable Long id) {
        log.debug("REST request to get MimeType : {}", id);
        Optional<MimeTypeDTO> mimeTypeDTO = mimeTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mimeTypeDTO);
    }

    /**
     * {@code DELETE  /mime-types/:id} : delete the "id" mimeType.
     *
     * @param id the id of the mimeTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mime-types/{id}")
    public ResponseEntity<Void> deleteMimeType(@PathVariable Long id) {
        log.debug("REST request to delete MimeType : {}", id);
        mimeTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
