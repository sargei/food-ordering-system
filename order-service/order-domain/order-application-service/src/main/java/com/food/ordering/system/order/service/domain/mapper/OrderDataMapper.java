package com.food.ordering.system.order.service.domain.mapper;

import com.food.ordering.system.domain.valueobject.CustomerId;
import com.food.ordering.system.domain.valueobject.Money;
import com.food.ordering.system.domain.valueobject.ProductId;
import com.food.ordering.system.domain.valueobject.RestaurantId;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.OrderItem;
import com.food.ordering.system.order.service.domain.entity.Product;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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

    public Order createOrderCommandToOrder(CreateOrderCommand orderCommand)
    {
        return Order.Builder.builder()
                .customerId(new CustomerId(orderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(orderCommand.getRestaurantId()))
                .deliveryAddress(orderAdressToStreetAddress(orderCommand.getAdress()))
                .price(new Money(orderCommand.getPrice()))
                .items(orderItemsToOrderItemsEntities(orderCommand.getItems()))
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemsEntities(List<com.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems)

    {
        return orderItems.stream().map(orderItem-> OrderItem.Builder.builder()
                .product(new Product(new ProductId(orderItem.getProductId())))
                        .price(new Money(orderItem.getPrice()))
                        .quantity(orderItem.getQuantity())
                        .subtotal(new Money(orderItem.getSubTotal()))
                        .build()
                                      ).toList();
    }

    private StreetAddress orderAdressToStreetAddress(OrderAddress orderAddress)
    {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
                );
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order)
    {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
