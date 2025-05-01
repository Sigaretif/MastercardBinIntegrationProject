package com.org.wortel.mastercardbin.infrastructure.persistence.repository;

import com.org.wortel.mastercardbin.infrastructure.persistence.entity.TransactionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<TransactionEntity> {

    public List<TransactionEntity> findByDatesAndCountryAndBinPrefix(LocalDateTime from, LocalDateTime to,
                                                                     String country, String binPrefix) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<TransactionEntity> criteriaQuery = criteriaBuilder.createQuery(TransactionEntity.class);
        Root<TransactionEntity> transactionRoot = criteriaQuery.from(TransactionEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (ObjectUtils.isNotEmpty(from)) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(transactionRoot.get("timestamp"), from));
        }
        if (ObjectUtils.isNotEmpty(to)) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(transactionRoot.get("timestamp"), to));
        }
        if (StringUtils.isNotEmpty(country)) {
            predicates.add(criteriaBuilder.equal(transactionRoot.get("location"), country));
        }
        if (StringUtils.isNotEmpty(binPrefix)) {
            predicates.add(criteriaBuilder.like(transactionRoot.get("binNumber"), binPrefix + "%"));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }
}