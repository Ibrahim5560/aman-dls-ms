package com.isoft.dls.web.rest;

import com.isoft.dls.service.ApplicationConfigurationService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.ApplicationConfigurationDTO;
import com.isoft.dls.service.dto.ApplicationConfigurationCriteria;
import com.isoft.dls.service.ApplicationConfigurationQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.ApplicationConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationConfigurationResource.class);

    private static final String ENTITY_NAME = "amanDlsmsApplicationConfiguration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationConfigurationService applicationConfigurationService;

    private final ApplicationConfigurationQueryService applicationConfigurationQueryService;

    public ApplicationConfigurationResource(ApplicationConfigurationService applicationConfigurationService, ApplicationConfigurationQueryService applicationConfigurationQueryService) {
        this.applicationConfigurationService = applicationConfigurationService;
        this.applicationConfigurationQueryService = applicationConfigurationQueryService;
    }

    /**
     * {@code POST  /application-configurations} : Create a new applicationConfiguration.
     *
     * @param applicationConfigurationDTO the applicationConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationConfigurationDTO, or with status {@code 400 (Bad Request)} if the applicationConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/application-configurations")
    public ResponseEntity<ApplicationConfigurationDTO> createApplicationConfiguration(@Valid @RequestBody ApplicationConfigurationDTO applicationConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to save ApplicationConfiguration : {}", applicationConfigurationDTO);
        if (applicationConfigurationDTO.getId() != null) {
            throw new BadRequestAlertException("A new applicationConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationConfigurationDTO result = applicationConfigurationService.save(applicationConfigurationDTO);
        return ResponseEntity.created(new URI("/api/application-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /application-configurations} : Updates an existing applicationConfiguration.
     *
     * @param applicationConfigurationDTO the applicationConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the applicationConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/application-configurations")
    public ResponseEntity<ApplicationConfigurationDTO> updateApplicationConfiguration(@Valid @RequestBody ApplicationConfigurationDTO applicationConfigurationDTO) throws URISyntaxException {
        log.debug("REST request to update ApplicationConfiguration : {}", applicationConfigurationDTO);
        if (applicationConfigurationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationConfigurationDTO result = applicationConfigurationService.save(applicationConfigurationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, applicationConfigurationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /application-configurations} : get all the applicationConfigurations.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applicationConfigurations in body.
     */
    @GetMapping("/application-configurations")
    public ResponseEntity<List<ApplicationConfigurationDTO>> getAllApplicationConfigurations(ApplicationConfigurationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ApplicationConfigurations by criteria: {}", criteria);
        Page<ApplicationConfigurationDTO> page = applicationConfigurationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /application-configurations/count} : count all the applicationConfigurations.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/application-configurations/count")
    public ResponseEntity<Long> countApplicationConfigurations(ApplicationConfigurationCriteria criteria) {
        log.debug("REST request to count ApplicationConfigurations by criteria: {}", criteria);
        return ResponseEntity.ok().body(applicationConfigurationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /application-configurations/:id} : get the "id" applicationConfiguration.
     *
     * @param id the id of the applicationConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-configurations/{id}")
    public ResponseEntity<ApplicationConfigurationDTO> getApplicationConfiguration(@PathVariable Long id) {
        log.debug("REST request to get ApplicationConfiguration : {}", id);
        Optional<ApplicationConfigurationDTO> applicationConfigurationDTO = applicationConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(applicationConfigurationDTO);
    }

    /**
     * {@code DELETE  /application-configurations/:id} : delete the "id" applicationConfiguration.
     *
     * @param id the id of the applicationConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/application-configurations/{id}")
    public ResponseEntity<Void> deleteApplicationConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete ApplicationConfiguration : {}", id);
        applicationConfigurationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
