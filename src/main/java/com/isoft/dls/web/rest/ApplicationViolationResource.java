package com.isoft.dls.web.rest;

import com.isoft.dls.service.ApplicationViolationService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.ApplicationViolationDTO;
import com.isoft.dls.service.dto.ApplicationViolationCriteria;
import com.isoft.dls.service.ApplicationViolationQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.ApplicationViolation}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationViolationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationViolationResource.class);

    private static final String ENTITY_NAME = "amanDlsmsApplicationViolation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationViolationService applicationViolationService;

    private final ApplicationViolationQueryService applicationViolationQueryService;

    public ApplicationViolationResource(ApplicationViolationService applicationViolationService, ApplicationViolationQueryService applicationViolationQueryService) {
        this.applicationViolationService = applicationViolationService;
        this.applicationViolationQueryService = applicationViolationQueryService;
    }

    /**
     * {@code POST  /application-violations} : Create a new applicationViolation.
     *
     * @param applicationViolationDTO the applicationViolationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationViolationDTO, or with status {@code 400 (Bad Request)} if the applicationViolation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-violations")
    public ResponseEntity<ApplicationViolationDTO> createApplicationViolation(@Valid @RequestBody ApplicationViolationDTO applicationViolationDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationViolation : {}", applicationViolationDTO);
        if (applicationViolationDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationViolation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationViolationDTO result = applicationViolationService.save(applicationViolationDTO);
        return ResponseEntity.created(new URI("/api/application-violations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-violations} : Updates an existing applicationViolation.
     *
     * @param applicationViolationDTO the applicationViolationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationViolationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationViolationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationViolationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-violations")
    public ResponseEntity<ApplicationViolationDTO> updateApplicationViolation(@Valid @RequestBody ApplicationViolationDTO applicationViolationDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationViolation : {}", applicationViolationDTO);
        if (applicationViolationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationViolationDTO result = applicationViolationService.save(applicationViolationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationViolationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-violations} : get all the applicationViolations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationViolations in body.
     */
    @GetMapping("/application-violations")
    public ResponseEntity<List<ApplicationViolationDTO>> getAllApplicationViolations(ApplicationViolationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicationViolations by criteria: {}", criteria);
        Page<ApplicationViolationDTO> page = applicationViolationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-violations/count} : count all the applicationViolations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/application-violations/count")
    public ResponseEntity<Long> countApplicationViolations(ApplicationViolationCriteria criteria) {
        log.debug("REST request to count ApplicationViolations by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationViolationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-violations/:id} : get the "id" applicationViolation.
     *
     * @param id the id of the applicationViolationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationViolationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-violations/{id}")
    public ResponseEntity<ApplicationViolationDTO> getApplicationViolation(@PathVariable Long id) {
        log.debug("REST request to get ApplicationViolation : {}", id);
        Optional<ApplicationViolationDTO> applicationViolationDTO = applicationViolationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationViolationDTO);
    }

    /**
     * {@code DELETE  /application-violations/:id} : delete the "id" applicationViolation.
     *
     * @param id the id of the applicationViolationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-violations/{id}")
    public ResponseEntity<Void> deleteApplicationViolation(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationViolation : {}", id);
        applicationViolationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
