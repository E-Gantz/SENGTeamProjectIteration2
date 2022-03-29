package SCSSoftware;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.CoinDispenser;

public class PaysWithCash {
	
	private PaysWithCoin payswithcoin;
	private BanknoteRunner banknoterunner;
	private BanknoteDispenser banknotedispenser;
	private CoinDispenser coindispenser;
	private BigDecimal chargedTotal;
	private BigDecimal totalValue;
	
	
	
	public PaysWithCash(PaysWithCoin payswithCoin, BanknoteRunner banknoteRunner, BanknoteDispenser banknoteDispenser, 
			CoinDispenser coinDispenser)
	{
		this.payswithcoin = payswithCoin;
		this.banknoterunner = banknoteRunner;
		this.coindispenser = coinDispenser;
		this.banknotedispenser = banknoteDispenser;
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
	
	public void emitChange()
	{
		if(totalValue.compareTo(BigDecimal.ZERO) >= 0)
		{
			// CoinStorageUnit load to CoinDispener then coindispenser.load and then coindispenser.emit()
			
		}
	}
	
}
