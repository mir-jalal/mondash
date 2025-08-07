package net.mirjalal.mondash.repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.mirjalal.mondash.model.Config;

import org.springframework.data.jpa.repository.JpaRepository;;

@Repository
public interface ConfigRepository extends JpaRepository<Config, BigInteger> {
    Optional<Config> findByKey(String key);
}
