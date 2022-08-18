package com.ashwetaw.dto.mapper;

import java.util.List;

public interface BaseMapper <D,E>{
    E toEntity(D dto);
    D toDTO(E entity);
    List<E> toEntities(List<D> dtoList);
    List <D> toDTOList(List<E> entityList);
}
