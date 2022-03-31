package SCSSoftwareTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.Acceptor;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.UnidirectionalChannel;

import SCSSoftware.PaysWithCoin;

public class PaysWithCoinTest {
	private Coin nickel;
	private Coin dime;
	private Coin quarter;
	private Coin loonie;
	private Coin toonie;
	
	private PaysWithCoin pwc;
    
	private CoinSlot slot;
	private Currency cad = Currency.getInstance("CAD");
	private ArrayList<BigDecimal> coinDenominations;
	private CoinValidator cValidator;
	
	private UnidirectionalChannel<Coin> reject;
	private Map<BigDecimal,UnidirectionalChannel<Coin>> standard;
	private UnidirectionalChannel<Coin> overflow;
	private UnidirectionalChannel<Coin> slotsink;
	
	private CoinStorageUnit storage;
	private CoinTray tray;
	private CoinDispenser coinDispenser;
	
	private Acceptor<Coin> rejAcceptor;
	private Acceptor<Coin> stanAcceptor;
	private Acceptor<Coin> overAcceptor;
	private Acceptor<Coin> sinkAcceptor;
	
	public Map<BigDecimal, CoinDispenser> coinDispensers;
	
	@Before
	public void setup()
	{
		Coin.DEFAULT_CURRENCY = cad;
		nickel = new Coin(BigDecimal.valueOf(0.05));
		dime = new Coin(BigDecimal.valueOf(0.10));
		quarter = new Coin(BigDecimal.valueOf(0.25));
		loonie = new Coin(BigDecimal.valueOf(1.00));
		toonie = new Coin(BigDecimal.valueOf(2.00));
		
		slot = new CoinSlot();
		coinDenominations = new ArrayList<BigDecimal>( Arrays.asList(nickel.getValue(), dime.getValue(), quarter.getValue(), loonie.getValue(), toonie.getValue()));
		cValidator = new CoinValidator(cad, coinDenominations);
		pwc = new PaysWithCoin(slot, cValidator);
		
		storage = new CoinStorageUnit(10000);
		tray = new CoinTray(100);
		storage.endConfigurationPhase();
		tray.endConfigurationPhase();
		
		coinDispensers = new HashMap<>();

        for(int i = 0; i < coinDenominations.size(); i++)
        {
            coinDispensers.put(coinDenominations.get(i), new CoinDispenser(10000));
		}
        
        for(CoinDispenser cd : coinDispensers.values())
            cd.endConfigurationPhase();
//		reject = new UnidirectionalChannel<Coin>(rejAcceptor);
//		standard = new HashMap<BigDecimal,UnidirectionalChannel<Coin>>();
//		overflow = new UnidirectionalChannel<Coin>(overAcceptor);
//		slotsink = new UnidirectionalChannel<Coin>(sinkAcceptor);
//	
//		slot.connect(slotsink);
//		for (BigDecimal denom : coinDenominations) {
//			Acceptor<Coin> acceptor = null;
//			standard.put(denom, new UnidirectionalChannel<Coin>(acceptor));
//		}
//		
//		cValidator.connect(reject, standard, overflow);
		
		
		interconnect(slot,cValidator);
		interconnect(cValidator, tray, coinDispensers, storage);
		
		cValidator.attach(pwc);
		cValidator.endConfigurationPhase();

	}
	
	private void interconnect(CoinValidator validator, CoinTray tray, Map<BigDecimal, CoinDispenser> dispensers,
	        CoinStorageUnit storage) {
	        UnidirectionalChannel<Coin> rejectChannel = new UnidirectionalChannel<Coin>(tray);
	        Map<BigDecimal, UnidirectionalChannel<Coin>> dispenserChannels = new HashMap<BigDecimal, UnidirectionalChannel<Coin>>();

	        for(BigDecimal denomination : dispensers.keySet()) {
	            CoinDispenser dispenser = dispensers.get(denomination);
	            dispenserChannels.put(denomination, new UnidirectionalChannel<Coin>(dispenser));
	        }

	        UnidirectionalChannel<Coin> overflowChannel = new UnidirectionalChannel<Coin>(storage);

	        validator.connect(rejectChannel, dispenserChannels, overflowChannel);
	    }
	
	private void interconnect(CoinSlot slot, CoinValidator validator) {
        UnidirectionalChannel<Coin> cc = new UnidirectionalChannel<Coin>(validator);
        slot.connect(cc);
        slot.endConfigurationPhase();
    }
	
    @Test
    public void insertCoinTest() throws DisabledException
    {
    	slot.accept(quarter);
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
    	BigDecimal expected = BigDecimal.valueOf(3.40);
    	
    	assertEquals(expected, (pwc.getTotal()).stripTrailingZeros());
    	//assertTrue(expected.equals(pwc.getTotalCoins()));
    }
    
    @Test
    public void getTotalTest()
    {
    	BigDecimal testSet = new BigDecimal(5.00);
    	pwc.setTotal(testSet);
    	assertEquals(testSet, pwc.getTotal());
    }
    
    @Test
    public void getTotalCoinsTest()
    {
    	BigDecimal testSet = new BigDecimal(5.00);
    	pwc.setTotal(testSet);
    	assertEquals(testSet, pwc.getTotalCoins());
    }
    
}