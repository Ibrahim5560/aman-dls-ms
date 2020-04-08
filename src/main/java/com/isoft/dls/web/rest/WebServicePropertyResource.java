package com.isoft.dls.web.rest;

import com.isoft.dls.service.WebServicePropertyService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.WebServicePropertyDTO;
import com.isoft.dls.service.dto.WebServicePropertyCriteria;
import com.isoft.dls.service.WebServicePropertyQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.WebServiceProperty}.
 */
@RestController
@RequestMapping("/api")
public class WebServicePropertyResource {

    private final Logger log = LoggerFactory.getLogger(WebServicePropertyResource.class);

    private static final String ENTITY_NAME = "amanDlsmsWebServiceProperty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebServicePropertyService webServicePropertyService;

    private final WebServicePropertyQueryService webServicePropertyQueryService;

    public WebServicePropertyResource(WebServicePropertyService webServicePropertyService, WebServicePropertyQueryService webServicePropertyQueryService) {
        this.webServicePropertyService = webServicePropertyService;
        this.webServicePropertyQueryService = webServicePropertyQueryService;
    }

    /**
     * {@code POST  /web-service-properties} : Create a new webServiceProperty.
     *
     * @param webServicePropertyDTO the webServicePropertyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webServicePropertyDTO, or with status {@code 400 (Bad Request)} if the webServiceProperty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-service-properties")
    public ResponseEntity<WebServicePropertyDTO> createWebServiceProperty(@Valid @RequestBody WebServicePropertyDTO webServicePropertyDTO) throws URISyntaxException {
        log.debug("REST request to save WebServiceProperty : {}", webServicePropertyDTO);
        if (webServicePropertyDTO.getId() != null) {
            throw new BadRequestAlertException("A new webServiceProperty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebServicePropertyDTO result = webServicePropertyService.save(webServicePropertyDTO);
        return ResponseEntity.created(new URI("/api/web-service-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-service-properties} : Updates an existing webServiceProperty.
     *
     * @param webServicePropertyDTO the webServicePropertyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webServicePropertyDTO,
     * or with status {@code 400 (Bad Request)} if the webServicePropertyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webServicePropertyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-service-properties")
    public ResponseEntity<WebServicePropertyDTO> updateWebServiceProperty(@Valid @RequestBody WebServicePropertyDTO webServicePropertyDTO) throws URISyntaxException {
        log.debug("REST request to update WebServiceProperty : {}", webServicePropertyDTO);
        if (webServicePropertyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebServicePropertyDTO result = webServicePropertyService.save(webServicePropertyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, webServicePropertyDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-service-properties} : get all the webServiceProperties.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webServiceProperties in body.
     */
    @GetMapping("/web-service-properties")
    public ResponseEntity<List<WebServicePropertyDTO>> getAllWebServiceProperties(WebServicePropertyCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WebServiceProperties by criteria: {}", criteria);
        Page<WebServicePropertyDTO> page = webServicePropertyQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /web-service-properties/count} : count all the webServiceProperties.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/web-service-properties/count")
    public ResponseEntity<Long> countWebServiceProperties(WebServicePropertyCriteria criteria) {
        log.debug("REST request to count WebServiceProperties by criteria: {}", criteria);
        return ResponseEntity.ok().body(webServicePropertyQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /web-service-properties/:id} : get the "id" webServiceProperty.
     *
     * @param id the id of the webServicePropertyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webServicePropertyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-service-properties/{id}")
    public ResponseEntity<WebServicePropertyDTO> getWebServiceProperty(@PathVariable Long id) {
        log.debug("REST request to get WebServiceProperty : {}", id);
        Optional<WebServicePropertyDTO> webServicePropertyDTO = webServicePropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webServicePropertyDTO);
    }

    /**
     * {@code DELETE  /web-service-properties/:id} : delete the "id" webServiceProperty.
     *
     * @param id the id of the webServicePropertyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-service-properties/{id}")
    public ResponseEntity<Void> deleteWebServiceProperty(@PathVariable Long id) {
        log.debug("REST request to delete WebServiceProperty : {}", id);
        webServicePropertyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
