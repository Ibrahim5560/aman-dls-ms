/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 3:04 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.ntf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.isoft.dls.web.rest.vm.ViewModel;
import io.swagger.annotations.ApiModel;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

@ApiModel(description = "Verify Otp Info Response VM. @author Rami Nassar.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OtpVerificationResponseVM implements ViewModel {

    @JsonProperty("status")
    private String status;

    @JsonProperty("id_token")
    private String token;

    @JsonProperty("tokenExpiry")
    private Date tokenExpiry;

    @JsonProperty("grantedAuthorities")
    private List<GrantedAuthority> grantedAuthorities;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpiry() {
        return tokenExpiry;
    }

    public void setTokenExpiry(Date tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String toString() {
        return "OtpVerificationResponseVM{" +
                "status='" + status + '\'' +
                ", token='" + token + '\'' +
                ", tokenExpiry=" + tokenExpiry +
                ", grantedAuthorities=" + grantedAuthorities +
                '}';
    }
}
