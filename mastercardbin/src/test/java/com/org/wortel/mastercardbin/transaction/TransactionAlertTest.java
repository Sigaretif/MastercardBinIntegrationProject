package com.org.wortel.mastercardbin.transaction;

import com.org.wortel.mastercardbin.application.errorhandling.general.BusinessExceptionResponse;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionRequestDto;
import com.org.wortel.mastercardbin.infrastructure.api.internal.dto.newtransaction.NewTransactionResponseDto;
import com.org.wortel.mastercardbin.util.AlertingTestProfile;
import com.org.wortel.mastercardbin.util.TestFixture;
import com.org.wortel.mastercardbin.util.TransactionTestHelper;
import com.org.wortel.mastercardbin.util.WiremockMastercardTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestProfile(AlertingTestProfile.class)
@QuarkusTestResource(WiremockMastercardTestResource.class)
class TransactionAlertTest {

    @Test
    void throwExceptionWhenAccessRateExceeded() {
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
        TransactionTestHelper.callCreateTransaction(request, 201, NewTransactionResponseDto.class);
        var errorResponse = TransactionTestHelper.callCreateTransaction(request, 429, BusinessExceptionResponse.class);

        // then
        assertEquals("Bin data access rate limit exceeded.", errorResponse.getGenericMessage());
        assertEquals(Response.Status.TOO_MANY_REQUESTS.getReasonPhrase(), errorResponse.getStatus());
        assertEquals(Response.Status.TOO_MANY_REQUESTS.getStatusCode(), errorResponse.getStatusCode());
    }
}
