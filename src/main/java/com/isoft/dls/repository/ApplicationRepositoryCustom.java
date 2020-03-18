/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/17/20 3:08 PM  - File created.
 */

package com.isoft.dls.repository;

import com.isoft.dls.domain.Application;

public interface ApplicationRepositoryCustom {
    Application saveJsonType(Application entity);
}
