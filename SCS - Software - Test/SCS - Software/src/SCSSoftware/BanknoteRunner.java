package SCSSoftware;
import softwareObservers.BanknoteObserver;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class BanknoteRunner {
	BanknoteObserver banknoteObserver;
	
	BanknoteSlot noteSlot;
	BanknoteDispenser noteDispenser;
	BanknoteStorageUnit noteStorage;
	BanknoteValidator noteValidator;
	
	public BanknoteRunner(SelfCheckoutStation station) {
		this.noteSlot = station.banknoteInput;
		this.noteStorage = station.banknoteStorage;
		this.noteValidator = station.banknoteValidator;
		
		this.banknoteObserver = new BanknoteObserver();
		this.attachObservers();
	}
	
	private void attachObservers() {
		noteSlot.attach(banknoteObserver);
		noteStorage.attach(banknoteObserver);
		noteValidator.attach(banknoteObserver);
	}
}
