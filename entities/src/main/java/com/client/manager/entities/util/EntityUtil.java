package com.client.manager.entities.util;

import com.client.manager.entities.BaseEntityProperties;
import com.client.manager.entities.status.StatusDefinedValue;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class EntityUtil {
    public static <T extends BaseEntityProperties> T setStatusEnabled(T t) {
        t.setStatus(StatusDefinedValue.ENABLED);
        return t;
    }

    public static <T extends BaseEntityProperties> T setStatusDisabled(T t) {
        t.setStatus(StatusDefinedValue.DISABLED);
        return t;
    }

    public static ZonedDateTime getCurrentZonedDateTime() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

    public static <T extends BaseEntityProperties> T setCurrentZonedDateTimeToCreatedDate(T t) {
        t.setCreatedDate(EntityUtil.getCurrentZonedDateTime());
        return t;
    }

    public static <T extends BaseEntityProperties> T setCurrentZonedDateTimeToUpdatedDate(T t) {
        t.setUpdatedDate(EntityUtil.getCurrentZonedDateTime());
        return t;
    }

    public static <T extends BaseEntityProperties> T setCreateBaseProperties(T t) {
        return EntityUtil.setCurrentZonedDateTimeToUpdatedDate(
                EntityUtil.setCurrentZonedDateTimeToCreatedDate(
                        EntityUtil.setStatusEnabled(
                                t
                        )
                )
        );
    }

    public static <T extends BaseEntityProperties> T setUpdateBaseProperties(T t) {
        return EntityUtil.setCurrentZonedDateTimeToUpdatedDate(
                EntityUtil.setStatusEnabled(
                        t
                )
        );
    }

    public static <T extends BaseEntityProperties> T setCreateOrUpdateBaseProperties(T t) {
        return Optional.ofNullable(t.getId()).isPresent() ?
                EntityUtil.setUpdateBaseProperties(t) :
                EntityUtil.setCreateBaseProperties(t);

    }

    public static <T extends BaseEntityProperties> T setDeleteBaseProperties(T t) {
        return EntityUtil.setCurrentZonedDateTimeToUpdatedDate(
                EntityUtil.setStatusDisabled(
                        t
                )
        );
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
