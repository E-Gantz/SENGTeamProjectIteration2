package softwareObservers;

import java.util.Currency;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BanknoteValidatorObserver;

import SCSSoftware.BanknoteRunner;

public class BValidatorObserver implements BanknoteValidatorObserver {
	
	BanknoteRunner runner;
	public BValidatorObserver(BanknoteRunner runner) {
		this.runner = runner;
	}
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void validBanknoteDetected(BanknoteValidator validator, Currency currency, int value) {
		// A valid banknote is detected
		runner.validNote(currency, value);
	}
	@Override
	public void invalidBanknoteDetected(BanknoteValidator validator) {
		// TODO Auto-generated method stub
		
	}
	
}
