package com.food.ordering.system.entity;

import com.food.ordering.system.valueobject.Money;
import com.food.ordering.system.valueobject.OrderId;
import com.food.ordering.system.valueobject.OrderItemId;
import lombok.Getter;

@Getter
public class OrderItem extends BaseEntity<OrderItemId>
{
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private Money subtotal;

    private OrderItem(Builder builder)
    {
        super.setId(builder.orderItemId);
        orderId = builder.orderId;
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subtotal = builder.subtotal;
    }


    public static final class Builder
    {
        private OrderItemId orderItemId;
        private OrderId orderId;
        private Product product;
        private int quantity;
        private Money price;
        private Money subtotal;

        private Builder()
        {
        }

        public static Builder builder()
        {
            return new Builder();
        }

        public Builder id(OrderItemId val)
        {
            orderItemId = val;
            return this;
        }

        public Builder orderId(OrderId val)
        {
            orderId = val;
            return this;
        }

        public Builder product(Product val)
        {
            product = val;
            return this;
        }

        public Builder quantity(int val)
        {
            quantity = val;
            return this;
        }

        public Builder price(Money val)
        {
            price = val;
            return this;
        }

        public Builder subtotal(Money val)
        {
            subtotal = val;
            return this;
        }

        public OrderItem build()
        {
            return new OrderItem(this);
        }
    }
}
