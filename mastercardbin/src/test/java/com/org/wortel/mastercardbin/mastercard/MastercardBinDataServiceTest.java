package com.org.wortel.mastercardbin.mastercard;

import com.org.wortel.mastercardbin.application.bindata.service.MastercardBinDataService;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardAuthorizationException;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardBadRequestException;
import com.org.wortel.mastercardbin.application.errorhandling.mastercard.MastercardBinDataNotFoundException;
import com.org.wortel.mastercardbin.util.TestFixture;
import com.org.wortel.mastercardbin.util.WiremockMastercardTestResource;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@QuarkusTestResource(WiremockMastercardTestResource.class)
class MastercardBinDataServiceTest {

    @Inject
    MastercardBinDataService mastercardBinDataService;

    @Test
    void successfullyGetBinData() {
        // given
        WiremockMastercardTestResource.mockSuccessfulCallWithSingleListElement(
                TestFixture.BIN_NUMBER_1, TestFixture.CUSTOMER_NAME_1, TestFixture.COUNTRY_NAME_1, TestFixture.COUNTRY_CODE_1
        );

        // when
        var response = mastercardBinDataService.getBinData(TestFixture.BIN_NUMBER_1);

        // then
        assertThat(response).hasSize(1);
        var binData = response.stream().findFirst().get();
        assertEquals(TestFixture.BIN_NUMBER_1, binData.getBinNumber());
        assertEquals(TestFixture.CUSTOMER_NAME_1, binData.getCustomerName());
        assertEquals(TestFixture.COUNTRY_NAME_1, binData.getCountryName());
        assertEquals(TestFixture.COUNTRY_CODE_1, binData.getCountryCode());
    }

    @Test
    void throwMastercardBadRequestException() {
        // given
        WiremockMastercardTestResource.mockUnsuccessfulCall(400);

        // when then
        assertThrows(MastercardBadRequestException.class, () -> mastercardBinDataService.getBinData(any()));
    }

    @ParameterizedTest
    @ValueSource(ints = {401, 403})
    void throwMastercardAuthorizationException(int status) {
        // given
        WiremockMastercardTestResource.mockUnsuccessfulCall(status);

        // when then
        assertThrows(MastercardAuthorizationException.class, () -> mastercardBinDataService.getBinData(any()));
    }

    @Test
    void throwMastercardBinDataNotFoundException() {
        // given
        WiremockMastercardTestResource.mockUnsuccessfulCall(404);

        // when then
        assertThrows(MastercardBinDataNotFoundException.class, () -> mastercardBinDataService.getBinData(any()));
    }

    @Test
    void throwMastercardProcessingException() {
        // given
        WiremockMastercardTestResource.mockUnsuccessfulCall(404);

        // when then
        assertThrows(MastercardBinDataNotFoundException.class, () -> mastercardBinDataService.getBinData(any()));
    }
}
