package com.client.manager.core.util;

import com.client.manager.core.exception.EntityNotFoundException;
import com.client.manager.entities.BaseEntityProperties;
import com.client.manager.entities.util.EntityUtil;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class BaseServiceUtil {
    private BaseServiceUtil() {
    }

    public static <E extends BaseEntityProperties> Function<Supplier<E>, E> setupForCreate() {
        return (entityToCreateSupplier) ->
                EntityUtil.applyBasePropertiesOnCreate(
                        entityToCreateSupplier.get()
                );
    }

    public static <K, E extends BaseEntityProperties> Function<Supplier<E>, E> setupForUpdate(
            Function<K, Optional<E>> findById
    ) throws EntityNotFoundException {
        return (entityToUpdateSupplier) ->
                EntityUtil.applyBasePropertiesOnUpdate(
                        EntityUtil.copyBaseProperties(
                                entityToUpdateSupplier.get(),
                                findById
                                        .apply((K) entityToUpdateSupplier.get().getId())
                                        .orElseThrow(EntityNotFoundException::new)
                        )
                );
    }

    public static <K, E extends BaseEntityProperties> Function<K, E> setupForDelete(
            Function<K, Optional<E>> findById
    ) throws EntityNotFoundException {
        return (id) ->
                EntityUtil.applyBasePropertiesOnDelete(
                        findById
                                .apply(id)
                                .orElseThrow(EntityNotFoundException::new)
                );
    }
}
