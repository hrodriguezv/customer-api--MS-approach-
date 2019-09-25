package com.client.manager.ui.service;

import org.springframework.http.ResponseEntity;

public interface IBaseService<T, K> {
    ResponseEntity save(T t);

    ResponseEntity deleteById(K id);

    ResponseEntity<T> update(T t);

    ResponseEntity<T> getById(K id);
}
