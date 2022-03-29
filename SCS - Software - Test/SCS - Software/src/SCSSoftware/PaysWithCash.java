package SCSSoftware;

import java.math.BigDecimal;

public class PaysWithCash {
	
	private PaysWithCoin payswithcoin;
	private BanknoteRunner banknoterunner;
	
	public PaysWithCash(PaysWithCoin payswithCoin, BanknoteRunner banknoteRunner)
	{
		this.payswithcoin = payswithCoin;
		this.banknoterunner = banknoteRunner;
	}
	
	public BigDecimal sumCoinBanknote()
	{
		BigDecimal totalCoins = new BigDecimal((payswithcoin.getTotalCoins()).floatValue());
		BigDecimal totalBanknotes = new BigDecimal((banknoterunner.sumBanknotes().floatValue()));
		BigDecimal totalValue =  new BigDecimal(0).add(totalCoins);
		totalValue.add(totalBanknotes);
		return totalValue;
	}
	
}
