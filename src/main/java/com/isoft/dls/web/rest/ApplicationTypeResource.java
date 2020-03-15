package com.isoft.dls.web.rest;

import com.isoft.dls.service.ApplicationTypeService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.ApplicationTypeDTO;
import com.isoft.dls.service.dto.ApplicationTypeCriteria;
import com.isoft.dls.service.ApplicationTypeQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.ApplicationType}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationTypeResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationTypeResource.class);

    private static final String ENTITY_NAME = "amanDlsmsApplicationType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationTypeService applicationTypeService;

    private final ApplicationTypeQueryService applicationTypeQueryService;

    public ApplicationTypeResource(ApplicationTypeService applicationTypeService, ApplicationTypeQueryService applicationTypeQueryService) {
        this.applicationTypeService = applicationTypeService;
        this.applicationTypeQueryService = applicationTypeQueryService;
    }

    /**
     * {@code POST  /application-types} : Create a new applicationType.
     *
     * @param applicationTypeDTO the applicationTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationTypeDTO, or with status {@code 400 (Bad Request)} if the applicationType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-types")
    public ResponseEntity<ApplicationTypeDTO> createApplicationType(@Valid @RequestBody ApplicationTypeDTO applicationTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationType : {}", applicationTypeDTO);
        if (applicationTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationTypeDTO result = applicationTypeService.save(applicationTypeDTO);
        return ResponseEntity.created(new URI("/api/application-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-types} : Updates an existing applicationType.
     *
     * @param applicationTypeDTO the applicationTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationTypeDTO,
     * or with status {@code 400 (Bad Request)} if the applicationTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-types")
    public ResponseEntity<ApplicationTypeDTO> updateApplicationType(@Valid @RequestBody ApplicationTypeDTO applicationTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationType : {}", applicationTypeDTO);
        if (applicationTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationTypeDTO result = applicationTypeService.save(applicationTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-types} : get all the applicationTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationTypes in body.
     */
    @GetMapping("/application-types")
    public ResponseEntity<List<ApplicationTypeDTO>> getAllApplicationTypes(ApplicationTypeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicationTypes by criteria: {}", criteria);
        Page<ApplicationTypeDTO> page = applicationTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /application-types/count} : count all the applicationTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/application-types/count")
    public ResponseEntity<Long> countApplicationTypes(ApplicationTypeCriteria criteria) {
        log.debug("REST request to count ApplicationTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-types/:id} : get the "id" applicationType.
     *
     * @param id the id of the applicationTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-types/{id}")
    public ResponseEntity<ApplicationTypeDTO> getApplicationType(@PathVariable Long id) {
        log.debug("REST request to get ApplicationType : {}", id);
        Optional<ApplicationTypeDTO> applicationTypeDTO = applicationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationTypeDTO);
    }

    /**
     * {@code DELETE  /application-types/:id} : delete the "id" applicationType.
     *
     * @param id the id of the applicationTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-types/{id}")
    public ResponseEntity<Void> deleteApplicationType(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationType : {}", id);
        applicationTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
