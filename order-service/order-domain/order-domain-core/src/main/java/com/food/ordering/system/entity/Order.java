package com.food.ordering.system.entity;

import com.food.ordering.system.valueobject.*;
import lombok.Getter;

import java.util.List;

@Getter
public class Order extends AggregateRoot<OrderId>
{
   private final CustomerId customerId;
   private final OrderId orderId;
   private final StreetAddress deliveryAddress;
   private final Money price;
   private final List<OrderItem>

   private TrackingId trackingId;
   private OrderStatus orderStatus;
   private List<String> failureMessages;

   private Order(Builder builder)
   {
      id = builder.id;
      customerId = builder.customerId;
      orderId = builder.orderId;
      deliveryAddress = builder.deliveryAddress;
      price = builder.price;
      trackingId = builder.trackingId;
      orderStatus = builder.orderStatus;
      failureMessages = builder.failureMessages;
   }


   public static final class Builder
   {
      private OrderId id;
      private CustomerId customerId;
      private OrderId orderId;
      private StreetAddress deliveryAddress;
      private Money price;
      private TrackingId trackingId;
      private OrderStatus orderStatus;
      private List<String> failureMessages;

      private Builder()
      {
      }

      public static Builder newBuilder()
      {
         return new Builder();
      }

      public Builder id(ID val)
      {
         id = val;
         return this;
      }

      public Builder customerId(CustomerId val)
      {
         customerId = val;
         return this;
      }

      public Builder orderId(OrderId val)
      {
         orderId = val;
         return this;
      }

      public Builder deliveryAddress(StreetAddress val)
      {
         deliveryAddress = val;
         return this;
      }

      public Builder price(Money val)
      {
         price = val;
         return this;
      }

      public Builder trackingId(TrackingId val)
      {
         trackingId = val;
         return this;
      }

      public Builder orderStatus(OrderStatus val)
      {
         orderStatus = val;
         return this;
      }

      public Builder failureMessages(List<String> val)
      {
         failureMessages = val;
         return this;
      }

      public Order build()
      {
         return new Order(this);
      }
   }
}
