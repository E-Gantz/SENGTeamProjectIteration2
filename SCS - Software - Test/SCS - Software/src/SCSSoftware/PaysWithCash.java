package SCSSoftware;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Map;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.EmptyException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SimulationException;

public class PaysWithCash {
	
	private PaysWithCoin payswithcoin;
	private BanknoteRunner banknoterunner;
	private Map<Integer, BanknoteDispenser> banknotedispenser;
	private Map<BigDecimal, CoinDispenser> coindispenser;
	private BigDecimal chargedTotal;
	private BigDecimal totalValue;
	private BanknoteSlot banknoteOutputSlot;
	private CoinTray coinTray;
	
	
	public PaysWithCash(PaysWithCoin payswithCoin, BanknoteRunner banknoteRunner, Map<Integer, BanknoteDispenser> banknoteDispenser, 
			Map<BigDecimal, CoinDispenser> coinDispenser, BanknoteSlot banknoteOutputSlot, CoinTray coinTray)
	{
		this.payswithcoin = payswithCoin;
		this.banknoterunner = banknoteRunner;
		this.coindispenser = coinDispenser;
		this.banknotedispenser = banknoteDispenser;
		this.banknoteOutputSlot = banknoteOutputSlot;
		this.coinTray = coinTray;
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
			for(Banknote note : listOfNotes) {
				if(banknotedispenser.containsKey(note.getValue())) {
					banknotedispenser.get(note.getValue()).load(note);
				}
			}
			for(Coin coin : listOfCoins) {
				if(coindispenser.containsKey(coin.getValue())) {
					coindispenser.get(coin.getValue()).load(coin);
				}
			}
			listOfNotes.toArray(notes);
			listOfCoins.toArray(coins);
			
			int count = 0;
			while(count < 5) {
				try {
					banknotedispenser.get(5).emit();
					banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 5));
				} catch (EmptyException e) {
					count++;
				}
				try {
					banknotedispenser.get(10).emit();
					banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 10));
				} catch (EmptyException e) {
					count++;
				}
				try {
					banknotedispenser.get(20).emit();
					banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 20));
				} catch (EmptyException e) {
					count++;
				}
				try {
					banknotedispenser.get(50).emit();
					banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 50));
				} catch (EmptyException e) {
					count++;
				}
				try {
					banknotedispenser.get(100).emit();
					banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 100));
				} catch (EmptyException e) {
					count++;
				}
			}
			
			int count1 = 0;
			while(count1 < 5) {
				try {
					coindispenser.get(BigDecimal.valueOf(0.05)).emit();
					coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.05)));
				} catch (EmptyException e) {
					count1++;
				}
				try {
					coindispenser.get(BigDecimal.valueOf(0.10)).emit();
					coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.10)));
				} catch (EmptyException e) {
					count1++;
				}
				try {
					coindispenser.get(BigDecimal.valueOf(0.25)).emit();
					coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.25)));
				} catch (EmptyException e) {
					count1++;
				}
				try {
					coindispenser.get(BigDecimal.valueOf(1.00)).emit();
					coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00)));
				} catch (EmptyException e) {
					count1++;
				}
				try {
					coindispenser.get(BigDecimal.valueOf(2.00)).emit();
					coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));
				} catch (EmptyException e) {
					count1++;
				}
			}
		}
	}
	
}
