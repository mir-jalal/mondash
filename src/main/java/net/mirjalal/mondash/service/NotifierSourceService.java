package net.mirjalal.mondash.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.mirjalal.mondash.model.NotifierSource;
import net.mirjalal.mondash.model.Source;
import net.mirjalal.mondash.model.factory.SourceFactory;
import net.mirjalal.mondash.repository.NotifierSourceRepository;

@Service
@AllArgsConstructor
public class NotifierSourceService extends SourceService {
    private final NotifierSourceRepository notifierSourceRepository;
    private final SourceFactory sourceFactory;

    @Override
    public Source saveSource(Source source) {
        return notifierSourceRepository.save( (NotifierSource) source);
    }

	@Override
	public SourceFactory getSourceFactory() {
        return this.sourceFactory;
	}

    @Override
    public Source getSourceById(Long id) {
        return notifierSourceRepository.getReferenceById(BigInteger.valueOf( id ));
    }

    @Override
    public List<Source> getAllSources() {
        return notifierSourceRepository.findAll().stream().map(s -> (Source) s).toList();
    }

    @Override
    public void delete(String name) {
        notifierSourceRepository.deleteByName(name);
    }
}
