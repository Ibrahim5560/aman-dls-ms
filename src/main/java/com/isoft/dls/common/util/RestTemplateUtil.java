/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 9:38 AM  - File created.
 */

package com.isoft.dls.common.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;

public class RestTemplateUtil {

    private RestTemplateUtil() {
        // Private Constructor..
    }

    /**
     * Exchange service using POST
     *
     * @param restTemplate Rest template
     * @param request Request object value
     * @param url Url
     * @param username Authentication sername
     * @param password Authentication Password
     * @param responseType Response type
     * @param uriVariables Uri variables
     * @param <T> Response data type
     * @param <E> Request data type
     * @return exchange result
     */
    public static <T,E> ResponseEntity<T> post(RestTemplate restTemplate,
                                               E request,
                                               String url,
                                               String username, String password,
                                               Class<T> responseType,
                                               Object... uriVariables) {

        HttpHeaders headers = createHeaders(username, password);
        HttpEntity<E> requestEntity;
        if(request != null) {
            requestEntity = new HttpEntity<>(request, headers);
        } else {
            requestEntity = new HttpEntity<>(headers);
        }
        // Exchange to Producer..
        return restTemplate.exchange (url,HttpMethod.POST,requestEntity,responseType,uriVariables);
    }

    /**
     * Exchange service using POST
     *
     * @param restTemplate Rest template
     * @param url Url
     * @param username Authentication username
     * @param password Authentication password
     * @param responseType Response type
     * @param uriVariables Uri variables
     * @param <T> Response data type
     * @param <E> Request data type
     * @return exchange result
     */
    public static <T,E> ResponseEntity<T> get(RestTemplate restTemplate,
                                              String url,
                                              String username,
                                              String password,
                                              Class<T> responseType,
                                              Object... uriVariables) {

        HttpHeaders headers = createHeaders(username, password);
        HttpEntity<E> requestEntity;
        requestEntity = new HttpEntity<>(headers);
        // Exchange to Producer..
        return restTemplate.exchange (url,HttpMethod.GET,requestEntity,responseType,uriVariables);
    }


    /**
     * Exchange service using PUT
     *
     * @param restTemplate Rest template
     * @param url Url
     * @param username Authentication username
     * @param password Authentication password
     * @param responseType Response type
     * @param uriVariables Uri variables
     * @param <T> Response data type
     * @param <E> Request data type
     * @return exchange result
     */
    public static <T,E> ResponseEntity<T> put(RestTemplate restTemplate,
                                              String url,
                                              String username,
                                              String password,
                                              Class<T> responseType,
                                              Object... uriVariables) {

        HttpHeaders headers = createHeaders(username, password);
        HttpEntity<E> requestEntity = new HttpEntity<>(headers);
        // Exchange to Producer..
        return restTemplate.exchange (url,HttpMethod.PUT,requestEntity,responseType,uriVariables);
    }


    /**
     * Create Headers
     *
     * @param username Username
     * @param password Password
     * @return Authenticated Header
     */
    public static HttpHeaders createHeaders(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String( encodedAuth );
        headers.set("Authorization", authHeader);
        headers.set( "ContentType", MediaType.APPLICATION_JSON_VALUE);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
}
