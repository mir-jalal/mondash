package net.mirjalal.mondash.model;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Data
@RequiredArgsConstructor
@Table(name = "alert_source_parameters")
public class AlertParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "alert_source_id")
    @EqualsAndHashCode.Exclude
    private AlertSource alertSource;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false, length = 4096)
    private String value;

    @Column(nullable = false)
    private boolean sensitive;
}
