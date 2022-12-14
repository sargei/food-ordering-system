package com.food.ordering.system.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BaseId<T>
{
    private final T value;

}
