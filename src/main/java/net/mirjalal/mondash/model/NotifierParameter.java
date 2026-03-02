package net.mirjalal.mondash.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "notifier_source_parameters")
public class NotifierParameter extends Parameter {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "notifier_source_id")
    @EqualsAndHashCode.Exclude
    private NotifierSource notifierSource;

    @Override
    public void setSource(Source source) {
        this.notifierSource = (NotifierSource) source;
    }

    @Override
    public Source getSource() {
        return this.notifierSource;
    }
}
