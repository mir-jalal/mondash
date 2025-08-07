package net.mirjalal.mondash.model;

import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Slf4j
@Data
@RequiredArgsConstructor
@Table(name = "conf", uniqueConstraints = @UniqueConstraint(columnNames = {"key"}))
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String key;
    private String value;

    public Config(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
