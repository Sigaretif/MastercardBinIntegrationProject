package com.org.wortel.mastercardbin.util;

import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.util.JwtTokenUtil;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@ApplicationScoped
public class TransactionTestHelper {

    @Getter
    private static Map<String, String> aggregateTransactionParamsMap = new HashMap<>();

    public static <C> C callCreateTransaction(NewTransactionRequestDto request, int statusCode, Class<C> responseClass) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + JwtTokenUtil.generateJwtToken())
                .body(request)
                .when()
                .post("/transactions")
                .then()
                .statusCode(statusCode)
                .extract()
                .as(responseClass);
    }

    public static <C> C callAggregateTransaction(Map<String, String> params, int statusCode, Class<C> responseClass) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .params(params)
                .header("Authorization", "Bearer " + JwtTokenUtil.generateJwtToken())
                .when()
                .get("/transactions/aggregate")
                .then()
                .statusCode(statusCode)
                .extract()
                .as(responseClass);
    }

    public static void clearAggregateTransactionParams() {
        aggregateTransactionParamsMap.clear();
    }

    public static void appendAggregateParamsWithFromDate(LocalDateTime fromDate) {
        aggregateTransactionParamsMap.put("from", fromDate.toString());
    }

    public static void appendAggregateParamsWithToDate(LocalDateTime toDate) {
        aggregateTransactionParamsMap.put("to", toDate.toString());
    }

    public static void appendAggregateParamsWithLocation(String location) {
        aggregateTransactionParamsMap.put("location", location);
    }

    public static void appendAggregateParamsWithBinPrefix(String binPrefix) {
        aggregateTransactionParamsMap.put("binPrefix", binPrefix);
    }

    public static Stream<Map<String, String>> provideInvalidAggregateTransactionRequest() {
        // Date parsing is not tested with ValidationExceptionResponse due to problem
        // described in https://github.com/quarkusio/quarkus/issues/40554
        return Stream.of(
                Map.of("binPrefix", TestFixture.INCORRECT_BIN_NUMBER_PREFIX),
                Map.of("from", TestFixture.TIMESTAMP.plusDays(1).toString(),
                        "to", TestFixture.TIMESTAMP.toString())
        );
    }

    public static Stream<NewTransactionRequestDto> provideInvalidCreateTransactionRequest() {
        return Stream.of(
                NewTransactionRequestDto.builder()
                        .binNumber(TestFixture.INCORRECT_BIN_NUMBER)
                        .amount(TestFixture.AMOUNT)
                        .location(TestFixture.LOCATION_1)
                        .timestamp(TestFixture.TIMESTAMP)
                        .build(),
                NewTransactionRequestDto.builder()
                        .binNumber(TestFixture.BIN_NUMBER_1)
                        .amount(TestFixture.INCORRECT_AMOUNT)
                        .location(TestFixture.LOCATION_1)
                        .timestamp(TestFixture.TIMESTAMP)
                        .build(),
                NewTransactionRequestDto.builder()
                        .binNumber(TestFixture.BIN_NUMBER_1)
                        .amount(TestFixture.AMOUNT)
                        .location(null)
                        .timestamp(TestFixture.TIMESTAMP)
                        .build(),
                NewTransactionRequestDto.builder()
                        .binNumber(TestFixture.BIN_NUMBER_1)
                        .amount(TestFixture.AMOUNT)
                        .location(TestFixture.LOCATION_1)
                        .timestamp(null)
                        .build()
        );
    }
}
