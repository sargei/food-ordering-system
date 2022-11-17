package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.domain.valueobject.*;
import com.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class Order extends AggregateRoot<OrderId>
{
   private final OrderId orderId;
   private final CustomerId customerId;
   private final StreetAddress deliveryAddress;
   private final Money price;
   private final List<OrderItem> items;
   private final RestaurantId restaurantId;

   private TrackingId trackingId;
   private OrderStatus orderStatus;
   private List<String> failureMessages;



   private Order(Builder builder)
   {
      super.setId(builder.orderID);
      customerId = builder.customerId;
      orderId = builder.orderId;
      deliveryAddress = builder.deliveryAddress;
      price = builder.price;
      items = builder.items;
      trackingId = builder.trackingId;
      orderStatus = builder.orderStatus;
      failureMessages = builder.failureMessages;
      restaurantId = builder.restaurantId;
   }

   public void initializeOrder()
   {
      setId(new OrderId(UUID.randomUUID()));
      trackingId = new TrackingId((UUID.randomUUID()));
      orderStatus = OrderStatus.PENDING;
      initializeOrderItems();
   }

   private void initializeOrderItems()
   {
      long itemId = 1;
      for(OrderItem item : items)
      {
         item.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
      }
   }

   public void validateOrder()
   {
      validateInitialOrder();
      validateTotalPrice();
      validateItemsPrice();
   }

   private void validateInitialOrder()
   {
      if (orderStatus != null || getId() != null)
         throw new OrderDomainException("Order is not in correct state for initialization");
   }

   private void validateTotalPrice()
   {
      if(price == null || !price.isGreaterThanZero())
         throw new OrderDomainException("Total price must be Greater than zero");
   }

   private void validateItemsPrice()
   {
      Money orderItemsTotal = items.stream().map(orderItem ->
      {
         validateItemPrice(orderItem);
         return orderItem.getSubtotal();
      }).reduce(Money.ZERO, Money::add);

      if(!price.equals(orderItemsTotal))
         throw new OrderDomainException("Total price : " + price.getAmount() +
                 "is not equal to Order items total" + orderItemsTotal.getAmount() + "!");
   }

   private void validateItemPrice(OrderItem orderItem)
   {
      if (!orderItem.isPriceValid())
         throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount() +
                 " is not valid for product " + orderItem.getProduct().getId().getValue());
   }




   public void pay()
   {
      if(orderStatus != OrderStatus.PENDING)
         throw new OrderDomainException("Order is not in pending state before pay operation");
      orderStatus = OrderStatus.PAID;
   }

   public void approve()
   {
      if (orderStatus != OrderStatus.PAID)
         throw new OrderDomainException("Order is not in correct state for approve operation !");
      orderStatus = OrderStatus.APPROVED;
   }

   public void initCancel(List<String> failureMessages)
   {
      if(orderStatus!=OrderStatus.PAID)
         throw new OrderDomainException("Order is not in correct state for initCancel operation !");
      orderStatus = OrderStatus.CANCELLING;
      updateFailureMessages(failureMessages);
   }

   public void cancel(List<String> failureMessages)
   {
      if(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)
         orderStatus = OrderStatus.CANCELLED;
      else
         throw new OrderDomainException("Order is not in correct state for cancel operation !");
      updateFailureMessages(failureMessages);
   }

   private void updateFailureMessages(List<String> failureMessages)
   {
      if(this.failureMessages != null && failureMessages != null)
         this.failureMessages.addAll(failureMessages.stream().filter(message -> !message.isEmpty()).toList());

      if(this.failureMessages == null)
         this.failureMessages = failureMessages;
   }



   public static final class Builder
   {
      private OrderId orderID;
      private CustomerId customerId;
      private OrderId orderId;
      private StreetAddress deliveryAddress;
      private Money price;
      private List<OrderItem> items;
      private TrackingId trackingId;
      private OrderStatus orderStatus;
      private List<String> failureMessages;

      private RestaurantId restaurantId;

      private Builder()
      {
      }

      public static Builder builder()
      {
         return new Builder();
      }

      public Builder id(OrderId val)
      {
         orderID = val;
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

      public Builder restaurantId(RestaurantId val)
      {
         restaurantId = val;
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

      public Builder items(List<OrderItem> val)
      {
         items = val;
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
