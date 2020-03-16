package com.isoft.dls.web.rest;

import com.isoft.dls.service.SysDomainValueService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.SysDomainValueDTO;
import com.isoft.dls.service.dto.SysDomainValueCriteria;
import com.isoft.dls.service.SysDomainValueQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.SysDomainValue}.
 */
@RestController
@RequestMapping("/api")
public class SysDomainValueResource {

    private final Logger log = LoggerFactory.getLogger(SysDomainValueResource.class);

    private static final String ENTITY_NAME = "amanDlsmsSysDomainValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysDomainValueService sysDomainValueService;

    private final SysDomainValueQueryService sysDomainValueQueryService;

    public SysDomainValueResource(SysDomainValueService sysDomainValueService, SysDomainValueQueryService sysDomainValueQueryService) {
        this.sysDomainValueService = sysDomainValueService;
        this.sysDomainValueQueryService = sysDomainValueQueryService;
    }

    /**
     * {@code POST  /sys-domain-values} : Create a new sysDomainValue.
     *
     * @param sysDomainValueDTO the sysDomainValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysDomainValueDTO, or with status {@code 400 (Bad Request)} if the sysDomainValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-domain-values")
    public ResponseEntity<SysDomainValueDTO> createSysDomainValue(@Valid @RequestBody SysDomainValueDTO sysDomainValueDTO) throws URISyntaxException {
        log.debug("REST request to save SysDomainValue : {}", sysDomainValueDTO);
        if (sysDomainValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysDomainValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysDomainValueDTO result = sysDomainValueService.save(sysDomainValueDTO);
        return ResponseEntity.created(new URI("/api/sys-domain-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-domain-values} : Updates an existing sysDomainValue.
     *
     * @param sysDomainValueDTO the sysDomainValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysDomainValueDTO,
     * or with status {@code 400 (Bad Request)} if the sysDomainValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysDomainValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-domain-values")
    public ResponseEntity<SysDomainValueDTO> updateSysDomainValue(@Valid @RequestBody SysDomainValueDTO sysDomainValueDTO) throws URISyntaxException {
        log.debug("REST request to update SysDomainValue : {}", sysDomainValueDTO);
        if (sysDomainValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysDomainValueDTO result = sysDomainValueService.save(sysDomainValueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysDomainValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-domain-values} : get all the sysDomainValues.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysDomainValues in body.
     */
    @GetMapping("/sys-domain-values")
    public ResponseEntity<List<SysDomainValueDTO>> getAllSysDomainValues(SysDomainValueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SysDomainValues by criteria: {}", criteria);
        Page<SysDomainValueDTO> page = sysDomainValueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /sys-domain-values/count} : count all the sysDomainValues.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/sys-domain-values/count")
    public ResponseEntity<Long> countSysDomainValues(SysDomainValueCriteria criteria) {
        log.debug("REST request to count SysDomainValues by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysDomainValueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-domain-values/:id} : get the "id" sysDomainValue.
     *
     * @param id the id of the sysDomainValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysDomainValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-domain-values/{id}")
    public ResponseEntity<SysDomainValueDTO> getSysDomainValue(@PathVariable Long id) {
        log.debug("REST request to get SysDomainValue : {}", id);
        Optional<SysDomainValueDTO> sysDomainValueDTO = sysDomainValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysDomainValueDTO);
    }

    /**
     * {@code DELETE  /sys-domain-values/:id} : delete the "id" sysDomainValue.
     *
     * @param id the id of the sysDomainValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-domain-values/{id}")
    public ResponseEntity<Void> deleteSysDomainValue(@PathVariable Long id) {
        log.debug("REST request to delete SysDomainValue : {}", id);
        sysDomainValueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
