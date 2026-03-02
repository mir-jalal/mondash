package net.mirjalal.mondash.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

// @Entity
@Data
// @Table(name = "alert_rule", uniqueConstraints= @UniqueConstraint(columnNames={"name"}))
public class AlertRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;
    private AlertSource alertSource;
    @EqualsAndHashCode.Exclude
    private List<Notification> parameters = new ArrayList<>();
}
