package com.org.wortel.mastercardbin.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessExceptionResponse;
import com.org.wortel.mastercardbin.infrastructure.api.errorhandling.ValidationExceptionResponse;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionResponseDto;
import com.org.wortel.mastercardbin.util.TestFixture;
import com.org.wortel.mastercardbin.util.TransactionTestHelper;
import com.org.wortel.mastercardbin.util.WiremockMastercardTestResource;
import io.quarkus.cache.Cache;
import io.quarkus.cache.CacheName;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(WiremockMastercardTestResource.class)
class CreateTransactionEndpointTest {

    @Inject
    @CacheName("transaction-metadata-cache")
    Cache transactionMetadataCache;

    @BeforeEach
    void setup() {
        WiremockMastercardTestResource.resetMockServer();
        transactionMetadataCache.invalidateAll().await().indefinitely();
    }

    @Test
    void throwSecurityError() {
        // given
        var request = NewTransactionRequestDto.builder()
                .binNumber(TestFixture.BIN_NUMBER_1)
                .amount(TestFixture.AMOUNT)
                .location(TestFixture.LOCATION_1)
                .timestamp(TestFixture.TIMESTAMP)
                .build();

        // when then
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/transactions")
                .then()
                .statusCode(401);
    }

    @ParameterizedTest
    @MethodSource("com.org.wortel.mastercardbin.util.TransactionTestHelper#provideInvalidCreateTransactionRequest")
    void throwValidationError(NewTransactionRequestDto request) {
        // when
        var response = TransactionTestHelper.callCreateTransaction(request, 400, ValidationExceptionResponse.class);

        // then
        assertEquals("There were issues with provided data", response.getErrorMessage());
        assertEquals(Response.Status.BAD_REQUEST.getReasonPhrase(), response.getStatus());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatusCode());
    }

    @Test
    void throwNonUniqueTransactionMetadataException() {
        // given
        WiremockMastercardTestResource.mockSuccessfulCallWithMultipleListElements(
                TestFixture.BIN_NUMBER_1, TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1);

        var request = NewTransactionRequestDto.builder()
                .binNumber(TestFixture.BIN_NUMBER_1)
                .amount(TestFixture.AMOUNT)
                .location(TestFixture.LOCATION_1)
                .timestamp(TestFixture.TIMESTAMP)
                .build();

        // when
        var response = TransactionTestHelper.callCreateTransaction(request, 409, BusinessExceptionResponse.class);

        // then
        assertEquals("Provided search criteria matched multiple entities.", response.getGenericMessage());
        assertEquals(Response.Status.CONFLICT.getReasonPhrase(), response.getStatus());
        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatusCode());
    }

    @Test
    void successfullyCreateTransaction() {
        // given
        WiremockMastercardTestResource.mockSuccessfulCallWithSingleListElement(
                TestFixture.BIN_NUMBER_1, TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1);

        var request = NewTransactionRequestDto.builder()
                .binNumber(TestFixture.BIN_NUMBER_1)
                .amount(TestFixture.AMOUNT)
                .location(TestFixture.LOCATION_1)
                .timestamp(TestFixture.TIMESTAMP)
                .build();

        // when
        var response = TransactionTestHelper.callCreateTransaction(request, 201, NewTransactionResponseDto.class);

        // then
        assertEquals(TestFixture.BIN_NUMBER_1, response.getBinNumber());
        assertEquals(TestFixture.AMOUNT, response.getAmount());
        assertEquals(TestFixture.LOCATION_1, response.getLocation());
        assertEquals(TestFixture.TIMESTAMP, response.getTimestamp());
        assertEquals(TestFixture.COUNTRY_CODE_1, response.getMetadata().getCountryCode());
        assertEquals(TestFixture.COUNTRY_NAME_1, response.getMetadata().getCountryName());
        assertEquals(TestFixture.CUSTOMER_NAME_1, response.getMetadata().getCustomerName());
    }

    @Test
    void successfullyCreateTransactionsWithSameMetadata() {
        // given
        WiremockMastercardTestResource.mockSuccessfulCallWithSingleListElement(
                TestFixture.BIN_NUMBER_1, TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1);

        var request = NewTransactionRequestDto.builder()
                .binNumber(TestFixture.BIN_NUMBER_1)
                .amount(TestFixture.AMOUNT)
                .location(TestFixture.LOCATION_1)
                .timestamp(TestFixture.TIMESTAMP)
                .build();

        // when
        var firstResponse = TransactionTestHelper.callCreateTransaction(request, 201, NewTransactionResponseDto.class);
        var secondResponse = TransactionTestHelper.callCreateTransaction(request, 201, NewTransactionResponseDto.class);

        // then
        assertEquals(firstResponse.getMetadata().getId(), secondResponse.getMetadata().getId());
        assertEquals(firstResponse.getMetadata().getCustomerName(), secondResponse.getMetadata().getCustomerName());
        assertEquals(firstResponse.getMetadata().getCountryName(), secondResponse.getMetadata().getCountryName());
        assertEquals(firstResponse.getMetadata().getCountryCode(), secondResponse.getMetadata().getCountryCode());
    }
}
