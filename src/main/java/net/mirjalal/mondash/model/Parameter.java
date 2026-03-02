package net.mirjalal.mondash.model;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@MappedSuperclass
public abstract class Parameter {

    public abstract void setSource(Source source);
    public abstract Source getSource();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "key", nullable = false)
    private String key;

    @Column(name = "value", nullable = false, length = 4096)
    private String value;

    @Column(nullable = false)
    private boolean sensitive;
    
}
