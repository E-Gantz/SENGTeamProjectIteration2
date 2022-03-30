package SCSSoftware;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CoinSlotObserver;
import org.lsmr.selfcheckout.devices.observers.CoinValidatorObserver;

public class PaysWithCoin implements CoinValidatorObserver{

	private CoinSlot slot;
	private CoinValidator validator;
	private Coin coin;
	private BigDecimal total;
	
	private ArrayList<BigDecimal> coinArray;
	
	public PaysWithCoin(CoinSlot slot, CoinValidator validator, Coin coin)
	{
		this.slot = slot;
		this.validator = validator;
		this.coin = coin;
		
	}
	
	public void acceptCoin(Coin insertedCoin) throws DisabledException {
		slot.accept(insertedCoin);
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}
	
	public void sumTotal(ArrayList<BigDecimal> list) {
		
		BigDecimal total = new BigDecimal(0);
		int i = 0;
		
		while(i < list.size()) {
			total.add(list.get(i));
			i++;
		}
		
		this.setTotalCoins(total);
	}
	
	 public void setTotalCoins(BigDecimal total)
     //setter method
	 {
		 this.total = total;
	 }

	 public BigDecimal getTotalCoins()
	 {   
		 return total;
	 }

	
	@Override
	public void validCoinDetected(CoinValidator validator, BigDecimal value) {
		coinArray.add(value);
	}

	@Override
	public void invalidCoinDetected(CoinValidator validator) {
		// TODO Auto-generated method stub
		
	}

}