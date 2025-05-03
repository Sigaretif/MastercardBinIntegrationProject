package com.org.wortel.mastercardbin.alerting;

import com.org.wortel.mastercardbin.application.errorhandling.transaction.BinAccessRateLimitExceedException;
import com.org.wortel.mastercardbin.application.transaction.service.BinAccessAlertService;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BinAccessAlertServiceTest {

    @Test
    void successfullyGetSigningKeyWhenCountThresholdNotExceeded() {
        // given
        var countThreshold = 3L;
        var binAccessService = new BinAccessAlertService("2S", countThreshold, true);

        // when
        LongStream.range(0, countThreshold).forEach(ignored -> {
            // then
            assertDoesNotThrow(() -> binAccessService.recordBinAccess("123456"));
        });
    }

    @Test
    void successfullyGetSigningKeyWhenBlockingDisabled() {
        // given
        var countThreshold = 3L;
        var binAccessService = new BinAccessAlertService("2S", countThreshold, false);

        // when
        LongStream.range(0, countThreshold + 1).forEach(ignored -> {
            // then
            assertDoesNotThrow(() -> binAccessService.recordBinAccess("123456"));
        });
    }

    @Test
    void throwExceptionWhenCountThresholdExceeded() {
        // given
        var countThreshold = 3L;
        var binAccessService = new BinAccessAlertService("2S", countThreshold, true);

        // when
        LongStream.range(0, countThreshold).forEach(ignored -> binAccessService.recordBinAccess("123456"));

        // then
        assertThrows(BinAccessRateLimitExceedException.class, () -> binAccessService.recordBinAccess("123456"));
    }
}
