package com.org.wortel.mastercardbin.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String binNumber;
    private BigDecimal amount;
    private String location;
    private Long timestamp;

    @PrePersist
    void prePersist() {
        this.timestamp = System.currentTimeMillis();
    }
}
