package uj.jwzp.w2.e3;

import java.math.BigDecimal;

public class Item {
    private String name;
    private BigDecimal price;

    Item(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
