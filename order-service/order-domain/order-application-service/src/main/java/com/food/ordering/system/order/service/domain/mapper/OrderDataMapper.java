package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderDataMapper
{
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand)
    {
        return Restaurant.Builder.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem -> new Product(new ProductId(orderItem.getProductId()))).toList())
                .build();

    }
}
