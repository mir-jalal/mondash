package net.mirjalal.mondash.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "notifier_source", uniqueConstraints= @UniqueConstraint(columnNames={"name"}))
public class NotifierSource extends Source {

    @OneToMany(
        mappedBy = "notifierSource",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @EqualsAndHashCode.Exclude
    private List<NotifierParameter> notifierParameters = new ArrayList<>();

    @Override
    public void setParameters(List<Parameter> parameters) {
        this.notifierParameters = parameters.stream().map(p -> (NotifierParameter) p).toList();
    }

    @Override
    public List<Parameter> getAllParameters() {
        return this.notifierParameters.stream().map(p -> (Parameter) p).toList();
    }
}
