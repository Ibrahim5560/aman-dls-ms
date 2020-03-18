package com.isoft.dls.web.rest;

import com.isoft.dls.service.ErrorLogService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.ErrorLogDTO;
import com.isoft.dls.service.dto.ErrorLogCriteria;
import com.isoft.dls.service.ErrorLogQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.ErrorLog}.
 */
@RestController
@RequestMapping("/api")
public class ErrorLogResource {

    private final Logger log = LoggerFactory.getLogger(ErrorLogResource.class);

    private static final String ENTITY_NAME = "amanDlsmsErrorLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErrorLogService errorLogService;

    private final ErrorLogQueryService errorLogQueryService;

    public ErrorLogResource(ErrorLogService errorLogService, ErrorLogQueryService errorLogQueryService) {
        this.errorLogService = errorLogService;
        this.errorLogQueryService = errorLogQueryService;
    }

    /**
     * {@code POST  /error-logs} : Create a new errorLog.
     *
     * @param errorLogDTO the errorLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new errorLogDTO, or with status {@code 400 (Bad Request)} if the errorLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/error-logs")
    public ResponseEntity<ErrorLogDTO> createErrorLog(@Valid @RequestBody ErrorLogDTO errorLogDTO) throws URISyntaxException {
        log.debug("REST request to save ErrorLog : {}", errorLogDTO);
        if (errorLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new errorLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ErrorLogDTO result = errorLogService.save(errorLogDTO);
        return ResponseEntity.created(new URI("/api/error-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /error-logs} : Updates an existing errorLog.
     *
     * @param errorLogDTO the errorLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errorLogDTO,
     * or with status {@code 400 (Bad Request)} if the errorLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the errorLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/error-logs")
    public ResponseEntity<ErrorLogDTO> updateErrorLog(@Valid @RequestBody ErrorLogDTO errorLogDTO) throws URISyntaxException {
        log.debug("REST request to update ErrorLog : {}", errorLogDTO);
        if (errorLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ErrorLogDTO result = errorLogService.save(errorLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, errorLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /error-logs} : get all the errorLogs.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of errorLogs in body.
     */
    @GetMapping("/error-logs")
    public ResponseEntity<List<ErrorLogDTO>> getAllErrorLogs(ErrorLogCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ErrorLogs by criteria: {}", criteria);
        Page<ErrorLogDTO> page = errorLogQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /error-logs/count} : count all the errorLogs.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/error-logs/count")
    public ResponseEntity<Long> countErrorLogs(ErrorLogCriteria criteria) {
        log.debug("REST request to count ErrorLogs by criteria: {}", criteria);
        return ResponseEntity.ok().body(errorLogQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /error-logs/:id} : get the "id" errorLog.
     *
     * @param id the id of the errorLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the errorLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/error-logs/{id}")
    public ResponseEntity<ErrorLogDTO> getErrorLog(@PathVariable Long id) {
        log.debug("REST request to get ErrorLog : {}", id);
        Optional<ErrorLogDTO> errorLogDTO = errorLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(errorLogDTO);
    }

    /**
     * {@code DELETE  /error-logs/:id} : delete the "id" errorLog.
     *
     * @param id the id of the errorLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/error-logs/{id}")
    public ResponseEntity<Void> deleteErrorLog(@PathVariable Long id) {
        log.debug("REST request to delete ErrorLog : {}", id);
        errorLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
