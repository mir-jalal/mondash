package net.mirjalal.mondash.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import net.mirjalal.mondash.model.Source;
import net.mirjalal.mondash.model.dto.SourceCreateDto;
import net.mirjalal.mondash.model.dto.SourceGetDto;
import net.mirjalal.mondash.model.factory.SourceFactory;
import net.mirjalal.mondash.model.factory.SourceFactory.SourceType;

public abstract class SourceService {
    public abstract Source saveSource(Source source);
    public abstract void delete(String name);
    public abstract Source getSourceById(Long id);
    public abstract List<Source> getAllSources();
    public abstract SourceFactory getSourceFactory();
    
    public BigInteger create(SourceCreateDto request, SourceType sourceType) {
        Source source = this.getSourceFactory().createSource(request, sourceType);
        source = this.saveSource(source);
        return source.getId();
    }

    public SourceGetDto get(Long id) {
        return this.getSourceFactory().createSourceGet(this.getSourceById(id));
    }

    public List<SourceGetDto> getAll() {
        List<SourceGetDto> sources = new ArrayList<>();
        getAllSources().stream()
          .forEach(source -> {
            sources.add(getSourceFactory().createSourceGet(source));
          });

        return sources;
    }
}
