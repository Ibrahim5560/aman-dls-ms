/*
 * Copyright (c) ISOFT 2020.
 * Ferdous Tower (Takreer Building) , Salam Street
 * Abu Dhabi, United Arab Emirates
 * P.O. Box: 32326
 * All Rights Reserved.
 *
 * ver    Developer          	Date              Comments
 * ----- -----------------  	----------       -----------------
 * 1.00  Eng. Ibrahim Hassanin 3/18/20 2:29 AM  - File created.
 */

package com.isoft.dls.web.rest.vm.trn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;

/**
 * A VM for the Attachment.
 */
@ApiModel(description = "Attachment VM entity. @author Mohammad Abulawi.")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AttachmentVM {

    @JsonProperty("nocAttachment")
    private String nocAttachment;

    @JsonProperty("nocAttachmentMimeType")
    private String nocAttachmentMimeType;

    public String getNocAttachment() {
        return nocAttachment;
    }

    public void setNocAttachment(String nocAttachment) {
        this.nocAttachment = nocAttachment;
    }

    public String getNocAttachmentMimeType() {
        return nocAttachmentMimeType;
    }

    public void setNocAttachmentMimeType(String nocAttachmentMimeType) {
        this.nocAttachmentMimeType = nocAttachmentMimeType;
    }


    @Override
    public String toString() {
        return "AttachmentVM{" +
            "nocAttachment='" + nocAttachment + '\'' +
            ", nocAttachmentMimeType='" + nocAttachmentMimeType + '\'' +
            '}';
    }
}
