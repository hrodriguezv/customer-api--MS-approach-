package com.client.manager.entities.util;

import com.client.manager.entities.status.StatusDefinedValue;

public class StatusUtil {
    private StatusUtil() {
    }

    public static StatusDefinedValue getStatusFrom(String status) {
        switch (status.toUpperCase()) {
            case "ENABLED":
                return StatusDefinedValue.ENABLED;
            case "DISABLED":
                return StatusDefinedValue.DISABLED;
            default:
                return StatusDefinedValue.ALL;
        }
    }
}
