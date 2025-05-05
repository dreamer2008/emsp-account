package com.xxx.emsp.account.util.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AppConstantsTest {

    @Test
    void contractIdFormat_ShouldMatchValidContractId() {
        String validContractId = "DE-ABC-123456789-A";
        assertTrue(validContractId.matches(AppConstants.CONTRACT_ID_FORMAT));
    }

    @Test
    void contractIdFormat_ShouldNotMatchInvalidContractId_MissingParts() {
        String invalidContractId = "DE-123456789";
        assertFalse(invalidContractId.matches(AppConstants.CONTRACT_ID_FORMAT));
    }

    @Test
    void contractIdFormat_ShouldNotMatchInvalidContractId_WrongFormat() {
        String invalidContractId = "123-ABC-DE-456789";
        assertFalse(invalidContractId.matches(AppConstants.CONTRACT_ID_FORMAT));
    }

    @Test
    void contractIdFormat_ShouldMatchValidContractId_WithOptionalParts() {
        String validContractId = "DEABC123456789";
        assertTrue(validContractId.matches(AppConstants.CONTRACT_ID_FORMAT));
    }

    @Test
    void contractIdFormat_ShouldBeCaseInsensitive() {
        String validContractId = "de-abc-123456789-a";
        assertTrue(validContractId.matches(AppConstants.CONTRACT_ID_FORMAT));
    }
}