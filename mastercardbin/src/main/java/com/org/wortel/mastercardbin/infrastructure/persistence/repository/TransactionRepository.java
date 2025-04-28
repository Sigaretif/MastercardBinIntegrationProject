package com.org.wortel.mastercardbin.infrastructure.persistence.repository;

import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<TransactionEntity> {

}