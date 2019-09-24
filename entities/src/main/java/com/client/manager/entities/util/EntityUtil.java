package com.client.manager.entities.util;

import com.client.manager.entities.BaseEntityProperties;
import com.client.manager.entities.status.StatusDefinedValue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Consumer;

public class EntityUtil {
    public static void setStatusEnabled(Consumer<StatusDefinedValue> setStatus) {
        setStatus.accept(StatusDefinedValue.ENABLED);
    }

    public static void setStatusDisabled(Consumer<StatusDefinedValue> setStatus) {
        setStatus.accept(StatusDefinedValue.DISABLED);
    }

    public static ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

    public static void setCurrentDate(Consumer<ZonedDateTime> setDate) {
        setDate.accept(EntityUtil.getCurrentZonedDateTime());
    }

    public static <T extends BaseEntityProperties> T applyBasePropertiesOnCreate(T t) {
        EntityUtil.setCurrentDate(
                t::setCreatedDate
        );

        EntityUtil.applyBasePropertiesOnUpdate(t);
        return t;
    }

    public static <T extends BaseEntityProperties> T applyBasePropertiesOnUpdate(T t) {
        EntityUtil.setStatusEnabled(
                t::setStatus
        );
        EntityUtil.setCurrentDate(
                t::setUpdatedDate
        );
        return t;
    }

    public static <T extends BaseEntityProperties> T applyBasePropertiesOnDelete(T t) {
        EntityUtil.setStatusDisabled(
                t::setStatus
        );
        EntityUtil.setCurrentDate(
                t::setUpdatedDate
        );
        return t;
    }

    public static <T extends BaseEntityProperties, S extends BaseEntityProperties> T copyBaseProperties(
            T target, S source
    ) {
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdatedDate(source.getUpdatedDate());
        target.setStatus(source.getStatus());
        return target;
    }
}
