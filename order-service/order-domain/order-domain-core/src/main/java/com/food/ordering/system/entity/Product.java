package com.food.ordering.system.entity;

import com.food.ordering.system.valueobject.Money;
import com.food.ordering.system.valueobject.ProductId;

public class Product extends BaseEntity<ProductId>
{
    private String name;

    public Product(ProductId id,String name, Money price)
    {
        super.setId(id);
        this.name = name;
        Price = price;
    }

    private Money Price;
}
