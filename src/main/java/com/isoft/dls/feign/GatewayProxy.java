/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 11:12 AM  - File created.
 */

package com.isoft.dls.feign;

import com.isoft.dls.client.AuthorizedFeignClient;
import com.isoft.dls.service.dto.ApplicationDTO;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AuthorizedFeignClient(name="gateway")
@RibbonClient(name="amanDLSMS")
public interface GatewayProxy {

    @GetMapping("services/amandlsms/api/getDetails/{applicationId}")
    ApplicationDTO getApplicationDto(@PathVariable("applicationId") Long applicationId);
}
