package com.nhl.spring_boot_example.mapper;

import java.util.List;

/**
 * Een interface om te standaardiseren hoe de mappers in elkaar steken.
 *
 * @param <T> Het type van de DTO klasse
 * @param <U> Het type van de Entity klasse
 */
public interface Mapper<T, U> {

    T toDTO(U u);

    /**
     * Convenient method om snel een lijst van entities om te zetten naar DTO's.
     *
     * @param u
     * @return
     */
    List<T> toDTO(List<U> u);

    U toEntity(T t);

}
