package net.mirjalal.mondash.repository;

import net.mirjalal.mondash.model.Alert;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends ElasticsearchRepository<Alert, String> {
}