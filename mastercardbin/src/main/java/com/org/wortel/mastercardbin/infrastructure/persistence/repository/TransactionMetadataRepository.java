package com.org.wortel.mastercardbin.infrastructure.persistence.repository;

import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionMetadataEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class TransactionMetadataRepository implements PanacheRepository<TransactionMetadataEntity> {

    public Optional<TransactionMetadataEntity> findByAllCredentials(String customerName, String countryName,
                                                                    Integer countryCode) {
        return find("customerName = ?1 and countryName = ?2 and countryCode = ?3",
                customerName, countryName, countryCode)
                .firstResultOptional();
    }
}