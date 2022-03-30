package SCSSoftwareTest;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

import SCSSoftware.PaysWithCoin;

public class PaysWithCoinTest {

	private Coin nickel = new Coin(BigDecimal.valueOf(0.05));
	private Coin dime = new Coin(BigDecimal.valueOf(0.10));
	private Coin quarter = new Coin(BigDecimal.valueOf(0.25));
	private Coin loonie = new Coin(BigDecimal.valueOf(1.00));
	private Coin toonie = new Coin(BigDecimal.valueOf(2.00));
    
	private CoinSlot slot = new CoinSlot();
	private Currency cad = Currency.getInstance("CAD");
	private ArrayList<BigDecimal> coinDenominations = new ArrayList<BigDecimal>( Arrays.asList(nickel.getValue(), dime.getValue(), quarter.getValue(), loonie.getValue(), toonie.getValue()));
	private CoinValidator cValidator = new CoinValidator(cad, coinDenominations);
    
	
    @Test
    public void insertCoinTest()
    {
    	PaysWithCoin pwc = new PaysWithCoin(slot, cValidator);
    	pwc.validCoinDetected(cValidator, quarter.getValue());
    	assertEquals(quarter.getValue(), pwc.getCoinArray().get(0));
    }
    
    @Test
    public void sumTotalTest()
    {
    	PaysWithCoin pwc = new PaysWithCoin(slot, cValidator);
    	pwc.getCoinArray().add(nickel.getValue());
    	pwc.getCoinArray().add(dime.getValue());
    	pwc.getCoinArray().add(quarter.getValue());
    	pwc.getCoinArray().add(loonie.getValue());
    	pwc.getCoinArray().add(toonie.getValue());
    	pwc.sumTotal(pwc.getCoinArray());
    	BigDecimal expected = new BigDecimal(3.40);
    	assertEquals(pwc.getTotal(), expected);
    }
    
    //@Test
    //public void coinInsertedTest(){
    //        SelfCheckoutStation selfCheckout = new SelfCheckoutStation(CAD, banknoteArray, coinArray, 1000, 1);
    //        PaysWithCoin pwc = new PaysWithCoin(selfCheckout.coinSlot, 
    //        		selfCheckout.coinValidator);
    //       Coin coin = new Coin(CAD, BigDecimal.valueOf(0.05));
    //        pwc.acceptCoin(coin);
    //}
    
}
