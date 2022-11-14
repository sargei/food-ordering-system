package com.food.ordering.system.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Getter
@EqualsAndHashCode
public class Money
{
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    private final BigDecimal amount;

    public Money(BigDecimal amount)
    {
        this.amount = amount;
    }

    public boolean isGreaterThanZero()
    {
        return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThan(Money money)
    {
        return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(Money amount)
    {
        return new Money(setScale(this.amount.add(amount.getAmount())));
    }

    public Money substract(Money amount)
    {
        return new Money(setScale(this.amount.subtract(amount.getAmount())));
    }

    public Money multiply(int multiplier)
    {
        return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
    }

    private BigDecimal setScale(BigDecimal input)
    {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
