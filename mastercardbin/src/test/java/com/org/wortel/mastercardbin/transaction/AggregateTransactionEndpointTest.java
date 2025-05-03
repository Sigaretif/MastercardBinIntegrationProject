package com.org.wortel.mastercardbin.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessExceptionResponse;
import com.org.wortel.mastercardbin.infrastructure.api.errorhandling.ValidationExceptionResponse;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.TransactionAggregateResponseDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCountryDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.transactionaggregate.valueobject.TransactionsPerCustomerDto;
import com.org.wortel.mastercardbin.util.TestFixture;
import com.org.wortel.mastercardbin.util.TransactionDatabaseHelper;
import com.org.wortel.mastercardbin.util.TransactionTestHelper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class AggregateTransactionEndpointTest {

    @Inject
    TransactionDatabaseHelper transactionDatabaseHelper;

    @BeforeEach
    void setup() {
        transactionDatabaseHelper.cleanDatabase();
        TransactionTestHelper.clearAggregateTransactionParams();
    }

    @Test
    void throwSecurityError() {
        // given when then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/transactions/aggregate")
                .then()
                .statusCode(401);
    }

    @Test
    void throwTransactionNotFoundException() {
        // given
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_2, BigDecimal.valueOf(1), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        TransactionTestHelper.appendAggregateParamsWithBinPrefix(TestFixture.BIN_NUMBER_1_PREFIX);

        // when
        var response = TransactionTestHelper.callAggregateTransaction(TransactionTestHelper.getAggregateTransactionParamsMap(),
                404, BusinessExceptionResponse.class);

        // then
        assertEquals("Provided search criteria did not match any entity.", response.getGenericMessage());
        assertEquals(Response.Status.NOT_FOUND.getReasonPhrase(), response.getStatus());
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    @ParameterizedTest
    @MethodSource("com.org.wortel.mastercardbin.util.TransactionTestHelper#provideInvalidAggregateTransactionRequest")
    void throwValidationError(Map<String, String> paramsMap) {
        // when
        var response = TransactionTestHelper.callAggregateTransaction(paramsMap, 400, ValidationExceptionResponse.class);

        // then
        assertEquals("There were issues with provided data", response.getErrorMessage());
        assertEquals(Response.Status.BAD_REQUEST.getReasonPhrase(), response.getStatus());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void getAggregatedTransactionsFilteredByParams() {
        // given
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_1, BigDecimal.valueOf(5), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_1, BigDecimal.valueOf(1), TestFixture.LOCATION_2, TestFixture.TIMESTAMP);
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_1, BigDecimal.valueOf(1), TestFixture.LOCATION_1, TestFixture.TIMESTAMP.plusDays(5));
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_2, TestFixture.COUNTRY_NAME_2, TestFixture.COUNTRY_CODE_2,
                TestFixture.BIN_NUMBER_2, BigDecimal.valueOf(1), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        TransactionTestHelper.appendAggregateParamsWithFromDate(TestFixture.TIMESTAMP.minusDays(1));
        TransactionTestHelper.appendAggregateParamsWithToDate(TestFixture.TIMESTAMP.plusDays(1));
        TransactionTestHelper.appendAggregateParamsWithLocation(TestFixture.LOCATION_1);
        TransactionTestHelper.appendAggregateParamsWithBinPrefix(TestFixture.BIN_NUMBER_1_PREFIX);
        var expectedCountryTransactions = List.of(
                TransactionsPerCountryDto.builder()
                        .countryName(TestFixture.COUNTRY_NAME_1)
                        .transactionsAmount(1L)
                        .build());

        // when
        var response = TransactionTestHelper.callAggregateTransaction(TransactionTestHelper.getAggregateTransactionParamsMap(),
                200, TransactionAggregateResponseDto.class);

        // then
        assertThat(response.getCountriesTransactionsSummary()).hasSize(1);
        assertThat(response.getMostFrequentCustomers()).hasSize(1);
        var mostFrequentCustomer = response.getMostFrequentCustomers().stream().findFirst().get();
        assertThat(BigDecimal.valueOf(5)).usingComparator(BigDecimal::compareTo).isEqualTo(response.getAverageTransactionsAmount());
        assertThat(expectedCountryTransactions).isEqualTo(response.getCountriesTransactionsSummary());
        assertEquals(TestFixture.CUSTOMER_NAME_1, mostFrequentCustomer.getCustomerName());
        assertEquals(1, mostFrequentCustomer.getTransactionsAmount());
    }

    @Test
    void getAggregatedTransactionsWithSingleMostFrequentCustomer() {
        // given
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_1, BigDecimal.valueOf(1), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_1, BigDecimal.valueOf(3), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        var expectedCountryTransactions = List.of(
                TransactionsPerCountryDto.builder()
                        .countryName(TestFixture.COUNTRY_NAME_1)
                        .transactionsAmount(2L)
                        .build());

        // when
        var response = TransactionTestHelper.callAggregateTransaction(Collections.emptyMap(), 200, TransactionAggregateResponseDto.class);

        // then
        assertThat(BigDecimal.valueOf(2)).usingComparator(BigDecimal::compareTo).isEqualTo(response.getAverageTransactionsAmount());
        assertThat(response.getMostFrequentCustomers()).hasSize(1);
        var mostFrequentCustomer = response.getMostFrequentCustomers().stream().findFirst().get();
        assertEquals(TestFixture.CUSTOMER_NAME_1, mostFrequentCustomer.getCustomerName());
        assertEquals(2, mostFrequentCustomer.getTransactionsAmount());
        assertThat(expectedCountryTransactions).isEqualTo(response.getCountriesTransactionsSummary());
    }

    @Test
    void getAggregatedTransactionsWithMultipleMostFrequentCustomer() {
        // given
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1,
                TestFixture.BIN_NUMBER_1, BigDecimal.valueOf(1), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        transactionDatabaseHelper.initData(TestFixture.CUSTOMER_NAME_2, TestFixture.COUNTRY_NAME_2, TestFixture.COUNTRY_CODE_2,
                TestFixture.BIN_NUMBER_2, BigDecimal.valueOf(3), TestFixture.LOCATION_1, TestFixture.TIMESTAMP);
        var expectedCountryTransactions = List.of(
                TransactionsPerCountryDto.builder()
                        .countryName(TestFixture.COUNTRY_NAME_1)
                        .transactionsAmount(1L)
                        .build(),
                TransactionsPerCountryDto.builder()
                        .countryName(TestFixture.COUNTRY_NAME_2)
                        .transactionsAmount(1L)
                        .build());
        var expectedMostFrequentCustomers = List.of(
                TransactionsPerCustomerDto.builder()
                        .customerName(TestFixture.CUSTOMER_NAME_1)
                        .transactionsAmount(1L)
                        .build(),
                TransactionsPerCustomerDto.builder()
                        .customerName(TestFixture.CUSTOMER_NAME_2)
                        .transactionsAmount(1L)
                        .build());
        var map = Map.of("from", TestFixture.TIMESTAMP.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")),
                "location", TestFixture.LOCATION_1);

        // when
        var response = TransactionTestHelper.callAggregateTransaction(map, 200, TransactionAggregateResponseDto.class);

        // then
        assertThat(BigDecimal.valueOf(2)).usingComparator(BigDecimal::compareTo).isEqualTo(response.getAverageTransactionsAmount());
        assertThat(expectedCountryTransactions).containsExactlyInAnyOrderElementsOf(response.getCountriesTransactionsSummary());
        assertThat(expectedMostFrequentCustomers).containsExactlyInAnyOrderElementsOf(response.getMostFrequentCustomers());
    }
}
