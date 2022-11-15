package com.food.ordering.system.order.service.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode
public class StreetAddress
{
    @EqualsAndHashCode.Exclude
    private final UUID id;
    private final String street;
    private final String postalCode;
    private final String city;

    public StreetAddress(UUID id, String street, String postalCode, String city)
    {
        this.id = id;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }



}
