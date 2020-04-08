package com.isoft.dls.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

import com.isoft.dls.domain.enumeration.PhaseType;

/**
 * ApplicationPhase (dls_application_phase) entity.\n@author Ibrahim Hassanin.
 */
@Entity
@Table(name = "apph")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ApplicationPhase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PhaseType type;

    @NotNull
    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @NotNull
    @Column(name = "persona", nullable = false)
    private String persona;

    @ManyToOne
    @JsonIgnoreProperties("applicationPhases")
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhaseType getType() {
        return type;
    }

    public ApplicationPhase type(PhaseType type) {
        this.type = type;
        return this;
    }

    public void setType(PhaseType type) {
        this.type = type;
    }

    public Integer getSequence() {
        return sequence;
    }

    public ApplicationPhase sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public ApplicationPhase startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ApplicationPhase endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getPersona() {
        return persona;
    }

    public ApplicationPhase persona(String persona) {
        this.persona = persona;
        return this;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public Application getApplication() {
        return application;
    }

    public ApplicationPhase application(Application application) {
        this.application = application;
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationPhase)) {
            return false;
        }
        return id != null && id.equals(((ApplicationPhase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationPhase{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", sequence=" + getSequence() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", persona='" + getPersona() + "'" +
            "}";
    }
}
