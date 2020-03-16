package com.isoft.dls.web.rest;

import com.isoft.dls.service.SysDomainService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.SysDomainDTO;
import com.isoft.dls.service.dto.SysDomainCriteria;
import com.isoft.dls.service.SysDomainQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.SysDomain}.
 */
@RestController
@RequestMapping("/api")
public class SysDomainResource {

    private final Logger log = LoggerFactory.getLogger(SysDomainResource.class);

    private static final String ENTITY_NAME = "amanDlsmsSysDomain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysDomainService sysDomainService;

    private final SysDomainQueryService sysDomainQueryService;

    public SysDomainResource(SysDomainService sysDomainService, SysDomainQueryService sysDomainQueryService) {
        this.sysDomainService = sysDomainService;
        this.sysDomainQueryService = sysDomainQueryService;
    }

    /**
     * {@code POST  /sys-domains} : Create a new sysDomain.
     *
     * @param sysDomainDTO the sysDomainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysDomainDTO, or with status {@code 400 (Bad Request)} if the sysDomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-domains")
    public ResponseEntity<SysDomainDTO> createSysDomain(@Valid @RequestBody SysDomainDTO sysDomainDTO) throws URISyntaxException {
        log.debug("REST request to save SysDomain : {}", sysDomainDTO);
        if (sysDomainDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysDomainDTO result = sysDomainService.save(sysDomainDTO);
        return ResponseEntity.created(new URI("/api/sys-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-domains} : Updates an existing sysDomain.
     *
     * @param sysDomainDTO the sysDomainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysDomainDTO,
     * or with status {@code 400 (Bad Request)} if the sysDomainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysDomainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-domains")
    public ResponseEntity<SysDomainDTO> updateSysDomain(@Valid @RequestBody SysDomainDTO sysDomainDTO) throws URISyntaxException {
        log.debug("REST request to update SysDomain : {}", sysDomainDTO);
        if (sysDomainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysDomainDTO result = sysDomainService.save(sysDomainDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysDomainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-domains} : get all the sysDomains.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysDomains in body.
     */
    @GetMapping("/sys-domains")
    public ResponseEntity<List<SysDomainDTO>> getAllSysDomains(SysDomainCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SysDomains by criteria: {}", criteria);
        Page<SysDomainDTO> page = sysDomainQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /sys-domains/count} : count all the sysDomains.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/sys-domains/count")
    public ResponseEntity<Long> countSysDomains(SysDomainCriteria criteria) {
        log.debug("REST request to count SysDomains by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysDomainQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-domains/:id} : get the "id" sysDomain.
     *
     * @param id the id of the sysDomainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysDomainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-domains/{id}")
    public ResponseEntity<SysDomainDTO> getSysDomain(@PathVariable Long id) {
        log.debug("REST request to get SysDomain : {}", id);
        Optional<SysDomainDTO> sysDomainDTO = sysDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysDomainDTO);
    }

    /**
     * {@code DELETE  /sys-domains/:id} : delete the "id" sysDomain.
     *
     * @param id the id of the sysDomainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-domains/{id}")
    public ResponseEntity<Void> deleteSysDomain(@PathVariable Long id) {
        log.debug("REST request to delete SysDomain : {}", id);
        sysDomainService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
