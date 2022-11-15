package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import lombok.Getter;

@Getter
public class Product extends BaseEntity<ProductId>
{
    private String name;

    public Product(ProductId id,String name, Money price)
    {
        super.setId(id);
        this.name = name;
        this.price = price;
    }

    private Money price;

    public void updateWithConfirmNameAndPrice(String name, Money price)
    {
        this.name = name;
        this.price = price;
    }
}
