package com.common.security.api.mapper;

import java.util.List;

public interface BaseMapper<T, E> {
    T toDto(E entity);

    E toEntity(T dto);

    List<T> toDtoList(List<E> entities);

    List<E> toEntityList(List<T> entities);
}
