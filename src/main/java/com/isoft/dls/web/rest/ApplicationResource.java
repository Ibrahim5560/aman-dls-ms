package com.isoft.dls.web.rest;

import com.isoft.dls.domain.enumeration.PhaseType;
import com.isoft.dls.service.ApplicationService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.ApplicationDTO;
import com.isoft.dls.service.dto.ApplicationCriteria;
import com.isoft.dls.service.ApplicationQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.Application}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationResource.class);

    private static final String ENTITY_NAME = "amanDlsmsApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationService applicationService;

    private final ApplicationQueryService applicationQueryService;

    public ApplicationResource(ApplicationService applicationService, ApplicationQueryService applicationQueryService) {
        this.applicationService = applicationService;
        this.applicationQueryService = applicationQueryService;
    }

    /**
     * {@code POST  /applications} : Create a new application.
     *
     * @param applicationDTO the applicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationDTO, or with status {@code 400 (Bad Request)} if the application has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/applications")
    public ResponseEntity<ApplicationDTO> createApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {
        log.debug("REST request to save Application : {}", applicationDTO);
        if (applicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new application cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationDTO result = applicationService.save(applicationDTO);
        return ResponseEntity.created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /applications} : Updates an existing application.
     *
     * @param applicationDTO the applicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/applications")
    public ResponseEntity<ApplicationDTO> updateApplication(@Valid @RequestBody ApplicationDTO applicationDTO) throws URISyntaxException {
        log.debug("REST request to update Application : {}", applicationDTO);
        if (applicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationDTO result = applicationService.save(applicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /applications} : get all the applications.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/applications")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(ApplicationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Applications by criteria: {}", criteria);
        Page<ApplicationDTO> page = applicationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /applications/count} : count all the applications.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/applications/count")
    public ResponseEntity<Long> countApplications(ApplicationCriteria criteria) {
        log.debug("REST request to count Applications by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /applications/:id} : get the "id" application.
     *
     * @param id the id of the applicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/applications/{id}")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationDTO);
    }

    /**
     * {@code DELETE  /applications/:id} : delete the "id" application.
     *
     * @param id the id of the applicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * PUT  /applications : Updates an existing application phase.
     *
     * @param id
     * @param activePhase
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationDTO,
     * or with status 400 (Bad Request) if the applicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationDTO couldn't be updated
     */
    @PutMapping("/applications/{id}/activePhase/{activePhase}/updateAppPhase")
    public ResponseEntity<ApplicationDTO> updateAppPhase(@PathVariable Long id, @PathVariable String activePhase) {
        log.debug("REST request to get Application : {} and active phase {} ", id, activePhase);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(id);

        if (applicationDTO.isPresent() && applicationDTO.get().getId() != null) {
            String currentActivePhase = applicationDTO.get().getActivePhase().value();
            if (!currentActivePhase.equals(activePhase)) {
                ApplicationDTO result = applicationService.updateAppPhaseCriteria(applicationDTO, PhaseType.valueOf(activePhase), null);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName,true,ENTITY_NAME, applicationDTO.get().getId().toString()))
                    .body(result);
            }
        }
        return null;
    }


    /**
     * PUT  /applications : Updates an existing application phase.
     *
     * @param id
     * @param activePhase
     * @param criteria
     * @return the ResponseEntity with status 200 (OK) and with body the updated applicationDTO,
     * or with status 400 (Bad Request) if the applicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the applicationDTO couldn't be updated
     */
    @PutMapping("/applications/{id}/activePhase/{activePhase}/criteria/{criteria}/updateAppPhase")
    public ResponseEntity<ApplicationDTO> updateAppPhaseCriteria(@PathVariable Long id, @PathVariable String activePhase, @PathVariable String criteria) {
        log.debug("REST request to get Application : {} and active phase {} and criteria {} ", id, activePhase, criteria);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(id);

        if (applicationDTO.isPresent() && applicationDTO.get().getId() != null) {
            String currentActivePhase = applicationDTO.get().getActivePhase().value();
            if (!currentActivePhase.equals(activePhase)) {
                ApplicationDTO result = applicationService.updateAppPhaseCriteria(applicationDTO, PhaseType.valueOf(activePhase), criteria);
                return ResponseEntity.ok()
                    .headers(HeaderUtil.createEntityUpdateAlert(applicationName,true,ENTITY_NAME, applicationDTO.get().getId().toString()))
                    .body(result);
            }
        }
        return null;
    }
}
