package uj.jwzp.w2.e3;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import uj.jwzp.w2.e3.external.DiscountsConfig;
import uj.jwzp.w2.e3.external.PersistenceLayer;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;

public class SellingServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private PersistenceLayer perLayer;

    @Test
    public void notSell() {
        //given
        SellingService uut = new SellingService(perLayer);
        Mockito.when(perLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Lojasiewicza");

        //when
        boolean sold = uut.sell(i, 7, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(10), uut.moneyService.getMoney(c));
    }

    @Test
    public void sell() {
        //given
        SellingService uut = new SellingService(perLayer);
        Mockito.when(perLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Lojasiewicza");

        //when
        boolean sold = uut.sell(i, 1, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(7), uut.moneyService.getMoney(c));
    }

    @Test
    public void sellALot() {
        //given
        SellingService uut = new SellingService(perLayer);
        DiscountsTestConfig config = mock(DiscountsTestConfig.class);
        uut.setDiscountsConfig(config);
        Mockito.when(perLayer.saveCustomer(Mockito.any())).thenReturn(Boolean.TRUE);
        Item i = new Item("i", new BigDecimal(3));
        Customer c = new Customer(1, "DasCustomer", "Kraków, Lojasiewicza");
        uut.moneyService.addMoney(c, new BigDecimal(990));
        Mockito.when(config.isWeekendPromotion()).thenReturn(false);
        Mockito.when(config.getDiscountForItem(i, c)).thenReturn(DiscountsConfig.getDiscountForItem(i, c));

        //when
        boolean sold = uut.sell(i, 10, c);

        //then
        Assert.assertFalse(sold);
        Assert.assertEquals(BigDecimal.valueOf(970), uut.moneyService.getMoney(c));
    }

    @Test
    public void mockTest() {
        //given
        SellingService uut = new SellingService(perLayer);
        DiscountsTestConfig config = mock(DiscountsTestConfig.class);
        uut.setDiscountsConfig(config);
        Mockito.when(config.isWeekendPromotion()).thenReturn(false);

        //when
        boolean promotion = uut.getDiscountsConfig().isWeekendPromotion();

        //then
        Assert.assertFalse(promotion);
    }
}
