package ru.frozolab.benzin.util;

import java.math.BigDecimal;

public class Money {
    public static final String RUB = "RUB";

    private String type;
    private BigDecimal amount;

    public Money(String type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThan(Money money) {
        return this.getAmount().compareTo(money.getAmount()) == 1;
    }

    public boolean isEqual(Money money) {
        return this.getAmount().compareTo(money.getAmount()) == 0;
    }

    public static Money parse(String type, String stringAmount) {
        BigDecimal amount = new BigDecimal(stringAmount);
        return new Money(type, amount.setScale(2, BigDecimal.ROUND_DOWN));
    }
}
