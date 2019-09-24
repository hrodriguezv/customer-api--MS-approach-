package com.client.manager.ws.validations;

import java.util.function.Supplier;

public class BaseControllerValidation {

    private BaseControllerValidation() {
    }

    public static void validateDuplicatedOnCreate(Supplier<Long> countRepeatedSupplier, Supplier<RuntimeException> e) {
        if (countRepeatedSupplier.get() > 0) {
            throw e.get();
        }
    }

}
