package SCSSoftware;
//test
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SimulationException;

public class PaysWithCash {
	
	private PaysWithCoin payswithcoin;
	private BanknoteRunner banknoterunner;
	private BanknoteDispenser banknotedispenser;
	private BanknoteStorageUnit banknotestorage;
	private CoinDispenser coindispenser;
	private CoinStorageUnit coinstorage;
	private BigDecimal chargedTotal;
	private BigDecimal totalValue;
	
	
	
	public PaysWithCash(PaysWithCoin payswithCoin, BanknoteRunner banknoteRunner, BanknoteDispenser banknoteDispenser, 
			CoinDispenser coinDispenser, BanknoteStorageUnit banknoteStorage, CoinStorageUnit coinStorage)
	{
		this.payswithcoin = payswithCoin;
		this.banknoterunner = banknoteRunner;
		this.coindispenser = coinDispenser;
		this.banknotedispenser = banknoteDispenser;
		this.banknotestorage = banknoteStorage;
		this.coinstorage = coinStorage;
	}
	
	public void sumCoinBanknote()
	{
		BigDecimal totalCoins = new BigDecimal((payswithcoin.getTotalCoins()).floatValue());
		BigDecimal totalBanknotes = new BigDecimal((banknoterunner.sumBanknotes().floatValue()));
		totalValue =  new BigDecimal(0).add(totalCoins);
		totalValue.add(totalBanknotes);
	}
	
	public void getChange(BanknoteRunner banknoterunner)
	{
		chargedTotal = new BigDecimal((banknoterunner.getCheckoutTotal().floatValue()));
		totalValue.subtract(chargedTotal);
	}
	
	public void emitChange() throws SimulationException, OverloadException, DisabledException
	{
		List<Banknote> listOfNotes = new ArrayList<Banknote>();
		List<Coin> listOfCoins = new ArrayList<Coin>();
		if(totalValue.compareTo(BigDecimal.ZERO) >= 0)
		{
			double totalValueDouble = totalValue.doubleValue();
			while(totalValueDouble != 0) {
				if(totalValueDouble - 100 >= 0) {
					totalValueDouble -= 100;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 100));
				}
				if(totalValueDouble - 50 >= 0) {
					totalValueDouble -= 50;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 50));
				}
				if(totalValueDouble - 20 >= 0) {
					totalValueDouble -= 20;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 20));
				}
				if(totalValueDouble - 10 >= 0) {
					totalValueDouble -= 10;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 10));
				}
				if(totalValueDouble - 5 >= 0) {
					totalValueDouble -= 5;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 5));
				}
				if(totalValueDouble - 2 >= 0) {
					totalValueDouble -= 2;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2)));
				}
				if(totalValueDouble - 1 >= 0) {
					totalValueDouble -= 1;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1)));
				}
				if(totalValueDouble - 0.25 >= 0) {
					totalValueDouble -= 0.25;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.25)));
				}
				if(totalValueDouble - 0.10 >= 0) {
					totalValueDouble -= 0.10;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.10)));
				}
				if(totalValueDouble - 0.05 >= 0) {
					totalValueDouble -= 0.05;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.05)));
				}
				if(totalValueDouble < 0.05 && totalValueDouble > 0) {
					if(totalValueDouble >= 0.03) {
						totalValueDouble = 0.05;
					} else {
						totalValueDouble = 0;
					}
				}
			}
			Banknote[] notes = {};
			Coin[] coins = {};
			listOfNotes.toArray(notes);
			listOfCoins.toArray(coins);
			banknotedispenser.load(notes);
			coindispenser.load(coins);
			
			banknotedispenser.endConfigurationPhase();
			coindispenser.endConfigurationPhase();
			banknotedispenser.enable();
			coindispenser.enable();
			
			while(true) {
				try {
					banknotedispenser.emit();
				} catch (EmptyException e) {
					break;
				}
			}
			while(true) {
				try {
					coindispenser.emit();
				} catch (EmptyException e) {
					break;
				}
			}
		}
	}
	
}
