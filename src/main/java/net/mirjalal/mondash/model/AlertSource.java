package net.mirjalal.mondash.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Data
@RequiredArgsConstructor
@Table(name = "alert_source", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class AlertSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;

    @OneToMany(
        mappedBy = "alertSource",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    @EqualsAndHashCode.Exclude
    private List<AlertParameter> parameters = new ArrayList<>();

    public Optional<AlertParameter> getParameter(String key) {
        return parameters.stream()
            .filter(p -> p.getKey().equals(key))
            .findFirst();
    }
}
