package com.org.wortel.mastercardbin.application.transaction.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.org.wortel.mastercardbin.application.errorhandling.transaction.BinAccessRateLimitExceedException;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@ApplicationScoped
public class BinAccessAlertService {

    private static final Logger LOGGER = Logger.getLogger(BinAccessAlertService.class.getName());
    private final Long countThreshold;
    private final boolean thresholdBlockingEnabled;
    private final Cache<String, AtomicInteger> binAccessCache;

    private enum TimeFormat {
        S, M, H
    }

    public BinAccessAlertService(
            @ConfigProperty(name = "bin-access.time-threshold") String timeThreshold,
            @ConfigProperty(name = "bin-access.count-threshold") Long countThreshold,
            @ConfigProperty(name = "bin-access.threshold-blocking-enabled") boolean thresholdBlockingEnabled) {
        this.countThreshold = countThreshold;
        this.thresholdBlockingEnabled = thresholdBlockingEnabled;
        this.binAccessCache = Caffeine.newBuilder()
                .expireAfterWrite(parseTimeThreshold(timeThreshold))
                .build();
    }

    public void recordBinAccess(String binNumber) {
        var counter = binAccessCache.get(binNumber, ignored -> new AtomicInteger(0));
        int accessCount = counter.incrementAndGet();

        if (accessCount > countThreshold) {
            processCountThresholdAction(binNumber);
        }
    }

    private Duration parseTimeThreshold(String timeThreshold) {
        var timeAmount = Long.parseLong(timeThreshold.substring(0, timeThreshold.length() - 1));
        var timeFormat = TimeFormat.valueOf(String.valueOf(timeThreshold.charAt(timeThreshold.length() - 1)));

        if (timeAmount <= 0) {
            throw new IllegalArgumentException("Invalid bin access time threshold format. Fix configuration");
        }
        return switch (timeFormat) {
            case S -> Duration.ofSeconds(timeAmount);
            case M -> Duration.ofMinutes(timeAmount);
            case H -> Duration.ofHours(timeAmount);
        };
    }

    private void processCountThresholdAction(String binNumber) {
        if (thresholdBlockingEnabled) {
            throw new BinAccessRateLimitExceedException(binNumber);
        }
        LOGGER.warning("Bin access count threshold exceeded for BIN: " + binNumber);
    }
}
