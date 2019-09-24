package com.client.manager.core.service;

import java.util.Optional;

public interface IBaseService<T, K> {
    T save(T t);

    T update(T t);

    Optional<T> findById(K id);
}
