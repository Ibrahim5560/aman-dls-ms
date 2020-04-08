package com.isoft.dls.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.isoft.dls.domain.MimeType} entity. This class is used
 * in {@link com.isoft.dls.web.rest.MimeTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mime-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MimeTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter extension;

    private StringFilter contentType;

    private IntegerFilter maximumSize;

    public MimeTypeCriteria(){
    }

    public MimeTypeCriteria(MimeTypeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.extension = other.extension == null ? null : other.extension.copy();
        this.contentType = other.contentType == null ? null : other.contentType.copy();
        this.maximumSize = other.maximumSize == null ? null : other.maximumSize.copy();
    }

    @Override
    public MimeTypeCriteria copy() {
        return new MimeTypeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getExtension() {
        return extension;
    }

    public void setExtension(StringFilter extension) {
        this.extension = extension;
    }

    public StringFilter getContentType() {
        return contentType;
    }

    public void setContentType(StringFilter contentType) {
        this.contentType = contentType;
    }

    public IntegerFilter getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(IntegerFilter maximumSize) {
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
        final MimeTypeCriteria that = (MimeTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(extension, that.extension) &&
            Objects.equals(contentType, that.contentType) &&
            Objects.equals(maximumSize, that.maximumSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        extension,
        contentType,
        maximumSize
        );
    }

    @Override
    public String toString() {
        return "MimeTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (extension != null ? "extension=" + extension + ", " : "") +
                (contentType != null ? "contentType=" + contentType + ", " : "") +
                (maximumSize != null ? "maximumSize=" + maximumSize + ", " : "") +
            "}";
    }

}
