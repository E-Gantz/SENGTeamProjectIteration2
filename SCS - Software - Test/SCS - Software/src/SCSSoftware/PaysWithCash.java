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

	public BigDecimal sumCoinBanknote()
	{
		BigDecimal totalCoins = new BigDecimal((payswithcoin.getTotalCoins()).floatValue());
		BigDecimal totalBanknotes = banknoterunner.sumBanknotes();
		totalValue =  new BigDecimal(0).add(totalCoins);
		this.totalValue = totalValue.add(totalBanknotes);
		return totalValue;
	}
	
	public BigDecimal getChange()
	{
		chargedTotal = banknoterunner.getCheckoutTotal();
		
		this.totalValue = totalValue.subtract(chargedTotal);
		
		return totalValue; 
	}
	
	public BigDecimal emitChange() throws SimulationException, OverloadException, DisabledException
	{
		BigDecimal changeReturned = BigDecimal.ZERO;
		List<Banknote> listOfNotes = new ArrayList<Banknote>();
		List<Coin> listOfCoins = new ArrayList<Coin>();
		if(totalValue.compareTo(BigDecimal.ZERO) >= 0)
		{
			double totalValueDouble = totalValue.doubleValue();
			while(totalValueDouble != 0) {
			
				if(totalValueDouble - 100 >= 0) {
					totalValueDouble -= 100;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 100));
				} else
				if (totalValueDouble - 50 >= 0) {
					totalValueDouble -= 50;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 50));
				}else
				if(totalValueDouble - 20 >= 0) {
					totalValueDouble -= 20;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 20));
				}else
				if(totalValueDouble - 10 >= 0) {
					totalValueDouble -= 10;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 10));
				}else
				if(totalValueDouble - 5 >= 0) {
					totalValueDouble -= 5;
					listOfNotes.add(new Banknote(Currency.getInstance("CAD"), 5));
				}else
				if(totalValueDouble - 2.00 >= 0) {
					totalValueDouble -= 2.00;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));
				}else
				if(totalValueDouble - 1.00 >= 0) {
					totalValueDouble -= 1.00;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00)));
				}else
				if(totalValueDouble - 0.25 >= 0) {
					totalValueDouble -= 0.25;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.25)));
				}else
				if(totalValueDouble - 0.10 >= 0) {
					totalValueDouble -= 0.10;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.10)));
				}else
				if(totalValueDouble - 0.05 >= 0) {
					totalValueDouble -= 0.05;
					listOfCoins.add(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.05)));
				}
				else if(totalValueDouble < 0.05 && totalValueDouble > 0) {
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
				while(count < 1) {
					try {
						banknotedispenser.get(100).emit();
//						banknotedispenser.get(100).
						banknoteOutputSlot.removeDanglingBanknote();
						banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 100));
						banknoteOutputSlot.removeDanglingBanknote();
						changeReturned = changeReturned.add(BigDecimal.valueOf(100));
						
					} catch (EmptyException e) {
						count++;
					}
				}
				while(count < 2) {
					try {
						banknotedispenser.get(50).emit();
						banknoteOutputSlot.removeDanglingBanknote();
						banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 50));
						banknoteOutputSlot.removeDanglingBanknote();
						changeReturned = changeReturned.add(BigDecimal.valueOf(50));
					} catch (EmptyException e) {
						count++;
					}
				}
				while(count < 3) {
					try {
						banknotedispenser.get(20).emit();
						banknoteOutputSlot.removeDanglingBanknote();
						banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 20));
						banknoteOutputSlot.removeDanglingBanknote();
						changeReturned = changeReturned.add(BigDecimal.valueOf(20));
					} catch (EmptyException e) {
						count++;
					}
				}
				while(count < 4) {
					try {
						banknotedispenser.get(10).emit();
						banknoteOutputSlot.removeDanglingBanknote();
						banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 10));
						banknoteOutputSlot.removeDanglingBanknote();
						changeReturned = changeReturned.add(BigDecimal.valueOf(10));
					} catch (EmptyException e) {
						count++;
					}
				}
				while(count < 5) {
					try {
						banknotedispenser.get(5).emit();
						banknoteOutputSlot.removeDanglingBanknote();
						banknoteOutputSlot.emit(new Banknote(Currency.getInstance("CAD"), 5));
						banknoteOutputSlot.removeDanglingBanknote();
						changeReturned = changeReturned.add(BigDecimal.valueOf(5));
					} catch (EmptyException e) {
						count++;
					}
				}
			}
			
			
			int count1 = 0;
			while(count1 < 5) {
				while(count1 < 1) {
					try {
						coindispenser.get(BigDecimal.valueOf(2.00)).emit();
						coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(2.00)));
						changeReturned = changeReturned.add(BigDecimal.valueOf(2.00));
					} catch (EmptyException e) {
						
						count1++;
						
					}
				}
				while(count1 < 2) {
					try {
						coindispenser.get(BigDecimal.valueOf(1.00)).emit();
						coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00)));
						changeReturned = changeReturned.add(BigDecimal.valueOf(1.00));
					} catch (EmptyException e) {
						count1++;
						
					}
				}
				while(count1 < 3) {
					try {
						coindispenser.get(BigDecimal.valueOf(0.25)).emit();
						coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.25)));
						changeReturned = changeReturned.add(BigDecimal.valueOf(0.25));
					} catch (EmptyException e) {
						count1++;
						
					}
				}
				while(count1 < 4) {
					try {
						coindispenser.get(BigDecimal.valueOf(0.10)).emit();
						coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.10)));
						changeReturned = changeReturned.add(BigDecimal.valueOf(0.10));
						
					} catch (EmptyException e) {
						count1++;
						
					}
				}
				while(count1 < 5) {
					try {
						coindispenser.get(BigDecimal.valueOf(0.05)).emit();
						coinTray.accept(new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(0.05)));
						changeReturned = changeReturned.add(BigDecimal.valueOf(0.05));
					} catch (EmptyException e) {
						count1++;
						
					}
				}
			}
		}
		
		return changeReturned;
	} 
	
}
