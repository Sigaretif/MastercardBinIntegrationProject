package com.org.wortel.mastercardbin.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.MastercardBinDataRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.MastercardBinDataResponseDto;
import com.org.wortel.mastercardbin.infrastructure.api.external.dto.valueobject.BinDataCountryDto;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WiremockMastercardTestResource implements QuarkusTestResourceLifecycleManager {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "/bin-ranges/account-searches";
    private static WireMockServer mockServer;

    @Override
    public Map<String, String> start() {
        mockServer = new WireMockServer(wireMockConfig().dynamicPort());
        mockServer.start();

        return Map.of("mastercard.base-url", mockServer.baseUrl());
    }

    @Override
    public void stop() {
        if (ObjectUtils.isNotEmpty(mockServer)) {
            mockServer.stop();
        }
    }

    public static void resetMockServer() {
        mockServer.resetAll();
    }

    @SneakyThrows
    public static void mockSuccessfulCallWithSingleListElement(String binNumber, String customerName, String countryName,
                                                               Integer countryCode) {
        var request = MastercardBinDataRequestDto.builder()
                .accountRange(binNumber)
                .build();
        var response = List.of(MastercardBinDataResponseDto.builder()
                .binNum(binNumber)
                .customerName(customerName)
                .country(BinDataCountryDto.builder()
                        .code(countryCode)
                        .name(countryName)
                        .build())
                .build());
        WiremockMastercardTestResource.mockServer
                .stubFor(WireMock.post(API_URL)
                        .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(request)))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(objectMapper.writeValueAsString(response))));
    }

    @SneakyThrows
    public static void mockSuccessfulCallWithMultipleListElements(String binNumber, String customerName, String countryName,
                                                                  Integer countryCode) {
        var request = MastercardBinDataRequestDto.builder()
                .accountRange(binNumber)
                .build();
        var responseDto = MastercardBinDataResponseDto.builder()
                .binNum(binNumber)
                .customerName(customerName)
                .country(BinDataCountryDto.builder()
                        .code(countryCode)
                        .name(countryName)
                        .build())
                .build();
        var response = List.of(responseDto, responseDto);
        WiremockMastercardTestResource.mockServer
                .stubFor(WireMock.post(API_URL)
                        .withRequestBody(WireMock.equalToJson(objectMapper.writeValueAsString(request)))
                        .willReturn(WireMock.aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(objectMapper.writeValueAsString(response))));
    }

    public static void mockUnsuccessfulCall(int statusCode) {
        WiremockMastercardTestResource.mockServer
                .stubFor(WireMock.post(API_URL)
                        .willReturn(WireMock.aResponse()
                                .withStatus(statusCode)
                                .withHeader("Content-Type", "application/json")
                                .withBody(String.format("Error %d", statusCode))));
    }
}
