package com.isoft.dls.web.rest;

import com.isoft.dls.service.WebServiceService;
import com.isoft.dls.web.rest.errors.BadRequestAlertException;
import com.isoft.dls.service.dto.WebServiceDTO;
import com.isoft.dls.service.dto.WebServiceCriteria;
import com.isoft.dls.service.WebServiceQueryService;

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
 * REST controller for managing {@link com.isoft.dls.domain.WebService}.
 */
@RestController
@RequestMapping("/api")
public class WebServiceResource {

    private final Logger log = LoggerFactory.getLogger(WebServiceResource.class);

    private static final String ENTITY_NAME = "amanDlsmsWebService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebServiceService webServiceService;

    private final WebServiceQueryService webServiceQueryService;

    public WebServiceResource(WebServiceService webServiceService, WebServiceQueryService webServiceQueryService) {
        this.webServiceService = webServiceService;
        this.webServiceQueryService = webServiceQueryService;
    }

    /**
     * {@code POST  /web-services} : Create a new webService.
     *
     * @param webServiceDTO the webServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webServiceDTO, or with status {@code 400 (Bad Request)} if the webService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-services")
    public ResponseEntity<WebServiceDTO> createWebService(@Valid @RequestBody WebServiceDTO webServiceDTO) throws URISyntaxException {
        log.debug("REST request to save WebService : {}", webServiceDTO);
        if (webServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new webService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebServiceDTO result = webServiceService.save(webServiceDTO);
        return ResponseEntity.created(new URI("/api/web-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-services} : Updates an existing webService.
     *
     * @param webServiceDTO the webServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webServiceDTO,
     * or with status {@code 400 (Bad Request)} if the webServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-services")
    public ResponseEntity<WebServiceDTO> updateWebService(@Valid @RequestBody WebServiceDTO webServiceDTO) throws URISyntaxException {
        log.debug("REST request to update WebService : {}", webServiceDTO);
        if (webServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebServiceDTO result = webServiceService.save(webServiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, webServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-services} : get all the webServices.
     *

     * @param pageable the pagination information.

     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webServices in body.
     */
    @GetMapping("/web-services")
    public ResponseEntity<List<WebServiceDTO>> getAllWebServices(WebServiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WebServices by criteria: {}", criteria);
        Page<WebServiceDTO> page = webServiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /web-services/count} : count all the webServices.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/web-services/count")
    public ResponseEntity<Long> countWebServices(WebServiceCriteria criteria) {
        log.debug("REST request to count WebServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(webServiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /web-services/:id} : get the "id" webService.
     *
     * @param id the id of the webServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-services/{id}")
    public ResponseEntity<WebServiceDTO> getWebService(@PathVariable Long id) {
        log.debug("REST request to get WebService : {}", id);
        Optional<WebServiceDTO> webServiceDTO = webServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webServiceDTO);
    }

    /**
     * {@code DELETE  /web-services/:id} : delete the "id" webService.
     *
     * @param id the id of the webServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-services/{id}")
    public ResponseEntity<Void> deleteWebService(@PathVariable Long id) {
        log.debug("REST request to delete WebService : {}", id);
        webServiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
