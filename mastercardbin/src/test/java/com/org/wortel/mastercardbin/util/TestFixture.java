package com.org.wortel.mastercardbin.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@UtilityClass
public class TestFixture {

    public final String BIN_NUMBER_1 = "123456";
    public final String BIN_NUMBER_1_PREFIX = "123";
    public final String INCORRECT_BIN_NUMBER_PREFIX = "123456789";
    public final String BIN_NUMBER_2 = "654321";
    public final String INCORRECT_BIN_NUMBER = "000";
    public final String CUSTOMER_NAME_1 = "Customer name 1";
    public final String CUSTOMER_NAME_2 = "Customer name 2";
    public final Integer COUNTRY_CODE_1 = 123;
    public final Integer COUNTRY_CODE_2 = 321;
    public final String COUNTRY_NAME_1 = "Country name 1";
    public final String COUNTRY_NAME_2 = "Country name 2";
    public final String LOCATION_1 = "City 1";
    public final String LOCATION_2 = "City 2";
    public final BigDecimal AMOUNT = BigDecimal.valueOf(1.00);
    public final BigDecimal INCORRECT_AMOUNT = BigDecimal.valueOf(0);
    public final LocalDateTime TIMESTAMP = LocalDateTime.now();
}
