package uj.jwzp.w2.e3;

import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

public class SellingService {

    protected final CustomerMoneyService moneyService;
    private final PersistenceLayer persistenceLayer;
    private DiscountsTestConfig discountsConfig;

    SellingService(PersistenceLayer persistenceLayer) {
        this.persistenceLayer = persistenceLayer;
        this.persistenceLayer.loadDiscountConfiguration();
        this.moneyService = new CustomerMoneyService(this.persistenceLayer);
        discountsConfig = new DiscountsTestConfig();
    }

    public boolean sell(Item item, int quantity, Customer customer) {
        BigDecimal price = item.getPrice().subtract(discountsConfig.getDiscountForItem(item, customer))
                .multiply(BigDecimal.valueOf(quantity));
        if (discountsConfig.isWeekendPromotion() && price.compareTo(BigDecimal.valueOf(5)) > 0) {
            price = price.subtract(BigDecimal.valueOf(3));
        }
        boolean sold = moneyService.pay(customer, price);
        return sold && persistenceLayer.saveTransaction(customer, item, quantity);
    }

    public DiscountsTestConfig getDiscountsConfig() {
        return discountsConfig;
    }

    public void setDiscountsConfig(DiscountsTestConfig discountsConfig) {
        this.discountsConfig = discountsConfig;
    }
}
