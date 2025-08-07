package net.mirjalal.mondash.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.mirjalal.mondash.model.AlertSource;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AlertSourceRepository extends JpaRepository<AlertSource, BigInteger> {
    void deleteByName(String name);

    AlertSource getByName(String string);
}
