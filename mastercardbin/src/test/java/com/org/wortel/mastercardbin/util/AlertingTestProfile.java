package com.org.wortel.mastercardbin.util;

import io.quarkus.test.junit.QuarkusTestProfile;

import java.util.Map;

public class AlertingTestProfile implements QuarkusTestProfile {

    public Map<String, String> getConfigOverrides() {
        return Map.of(
                "bin-access.time-threshold", "2S",
                "bin-access.count-threshold", "1",
                "bin-access.threshold-blocking-enabled", "true"
        );
    }
}
