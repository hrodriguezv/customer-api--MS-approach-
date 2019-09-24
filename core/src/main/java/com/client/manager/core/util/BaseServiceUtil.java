package com.client.manager.core.util;

import com.client.manager.entities.BaseEntityProperties;
import com.client.manager.entities.util.EntityUtil;

import java.util.function.Function;
import java.util.function.Supplier;

public class BaseServiceUtil {
    private BaseServiceUtil() {
    }

    public static <E extends BaseEntityProperties> Function<E, E> setupForCreate() {
        return EntityUtil::applyBasePropertiesOnCreate;
    }

    public static <E extends BaseEntityProperties> Function<E, E> setupForUpdate(
            E entity
    ) {
        return (entityToUpdate) ->
                EntityUtil.applyBasePropertiesOnUpdate(
                        EntityUtil.copyBaseProperties(entityToUpdate, entity)
                );
    }

    public static <E extends BaseEntityProperties> Supplier<E> setupForDelete(E entity) {
        return () -> EntityUtil.applyBasePropertiesOnDelete(entity);
    }
}
