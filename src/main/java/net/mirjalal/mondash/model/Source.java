package net.mirjalal.mondash.model;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.mirjalal.mondash.exception.NotFoundException;

@Data
@MappedSuperclass
@RequiredArgsConstructor
public abstract class Source {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;

    public abstract List<Parameter> getAllParameters();
    public abstract void setParameters(List<Parameter> parameters);

    public String getParameterValue(String key) {
        Parameter parameter = this.getParameter(key);
        return parameter.getValue();
    }

    private Optional<Parameter> getOptionalParameter(String key) {
        return getAllParameters().stream()
            .filter(p -> p.getKey().equals(key))
            .findFirst();
    }

    public Parameter getParameter(String key) {
        Optional<Parameter> parameter = this.getOptionalParameter(key);
        if (parameter.isEmpty())
            throw new NotFoundException("Cannot find the key: " + key);
        else
            return parameter.get();
    }
}
