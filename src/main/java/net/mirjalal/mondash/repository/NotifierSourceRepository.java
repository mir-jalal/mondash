package net.mirjalal.mondash.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import net.mirjalal.mondash.model.NotifierSource;

@Repository
@Transactional
public interface NotifierSourceRepository extends JpaRepository<NotifierSource, BigInteger> {
    void deleteByName(String name);

    NotifierSource getByName(String string);
}
