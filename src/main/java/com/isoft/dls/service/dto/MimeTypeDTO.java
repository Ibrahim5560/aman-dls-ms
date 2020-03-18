package com.isoft.dls.service.dto;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.dls.domain.MimeType} entity.
 */
@ApiModel(description = "Mime Type (sys_mime_type) entity.\n@author Ibrahim Hassanin.")
public class MimeTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String extension;

    @NotNull
    private String contentType;

    @NotNull
    private Integer maximumSize;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MimeTypeDTO mimeTypeDTO = (MimeTypeDTO) o;
        if (mimeTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mimeTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MimeTypeDTO{" +
            "id=" + getId() +
            ", extension='" + getExtension() + "'" +
            ", contentType='" + getContentType() + "'" +
            ", maximumSize=" + getMaximumSize() +
            "}";
    }
}
