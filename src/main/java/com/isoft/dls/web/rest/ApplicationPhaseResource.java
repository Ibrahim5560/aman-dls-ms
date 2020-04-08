package com.isoft.dls.web.rest;

import com.isoft.dls.service.ApplicationPhaseService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.ApplicationPhaseDTO;
import com.isoft.dls.service.dto.ApplicationPhaseCriteria;
import com.isoft.dls.service.ApplicationPhaseQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.ApplicationPhase}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationPhaseResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationPhaseResource.class);

    private static final String ENTITY_NAME = "amanDlsmsApplicationPhase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationPhaseService applicationPhaseService;

    private final ApplicationPhaseQueryService applicationPhaseQueryService;

    public ApplicationPhaseResource(ApplicationPhaseService applicationPhaseService, ApplicationPhaseQueryService applicationPhaseQueryService) {
        this.applicationPhaseService = applicationPhaseService;
        this.applicationPhaseQueryService = applicationPhaseQueryService;
    }

    /**
     * {@code POST  /application-phases} : Create a new applicationPhase.
     *
     * @param applicationPhaseDTO the applicationPhaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationPhaseDTO, or with status {@code 400 (Bad Request)} if the applicationPhase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-phases")
    public ResponseEntity<ApplicationPhaseDTO> createApplicationPhase(@Valid @RequestBody ApplicationPhaseDTO applicationPhaseDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationPhase : {}", applicationPhaseDTO);
        if (applicationPhaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationPhase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationPhaseDTO result = applicationPhaseService.save(applicationPhaseDTO);
        return ResponseEntity.created(new URI("/api/application-phases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-phases} : Updates an existing applicationPhase.
     *
     * @param applicationPhaseDTO the applicationPhaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationPhaseDTO,
     * or with status {@code 400 (Bad Request)} if the applicationPhaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationPhaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-phases")
    public ResponseEntity<ApplicationPhaseDTO> updateApplicationPhase(@Valid @RequestBody ApplicationPhaseDTO applicationPhaseDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationPhase : {}", applicationPhaseDTO);
        if (applicationPhaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationPhaseDTO result = applicationPhaseService.save(applicationPhaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationPhaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-phases} : get all the applicationPhases.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationPhases in body.
     */
    @GetMapping("/application-phases")
    public ResponseEntity<List<ApplicationPhaseDTO>> getAllApplicationPhases(ApplicationPhaseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicationPhases by criteria: {}", criteria);
        Page<ApplicationPhaseDTO> page = applicationPhaseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-phases/count} : count all the applicationPhases.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/application-phases/count")
    public ResponseEntity<Long> countApplicationPhases(ApplicationPhaseCriteria criteria) {
        log.debug("REST request to count ApplicationPhases by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationPhaseQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-phases/:id} : get the "id" applicationPhase.
     *
     * @param id the id of the applicationPhaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationPhaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-phases/{id}")
    public ResponseEntity<ApplicationPhaseDTO> getApplicationPhase(@PathVariable Long id) {
        log.debug("REST request to get ApplicationPhase : {}", id);
        Optional<ApplicationPhaseDTO> applicationPhaseDTO = applicationPhaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationPhaseDTO);
    }

    /**
     * {@code DELETE  /application-phases/:id} : delete the "id" applicationPhase.
     *
     * @param id the id of the applicationPhaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-phases/{id}")
    public ResponseEntity<Void> deleteApplicationPhase(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationPhase : {}", id);
        applicationPhaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
