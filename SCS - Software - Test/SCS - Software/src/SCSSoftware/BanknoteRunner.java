package SCSSoftware;
import softwareObservers.BanknoteObserver;
import org.lsmr.selfcheckout.devices.BanknoteSlot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;

public class BanknoteRunner {
	
	// Initialize the observer and define the hardware so we have access to them throughout the class
	private BanknoteObserver banknoteObserver;
	
	private BanknoteSlot noteSlot;
	private BanknoteDispenser noteDispenser;
	private BanknoteStorageUnit noteStorage;
	private BanknoteValidator noteValidator;
	
	// Paid total is the amount of currency that has been inputted and successfully validated in the machine
	// Checkout total is the amount of currency that needs to be paid
	private BigDecimal paidTotal;
	private BigDecimal checkoutTotal;
	
	// List of all banknotes that have been deposited and successfully validated
	private ArrayList<Banknote> banknoteCart;
	
	// BanknoteRunner is passed the checkout's total along with the station
	public BanknoteRunner(BigDecimal checkoutTotal, SelfCheckoutStation station) {
		this.noteSlot = station.banknoteInput;
		this.noteStorage = station.banknoteStorage;
		this.noteValidator = station.banknoteValidator;
		
		this.paidTotal = BigDecimal.ZERO;
		this.checkoutTotal = checkoutTotal;
		
		this.banknoteCart = new ArrayList<Banknote>();
		
		this.banknoteObserver = new BanknoteObserver(this);
		this.attachObservers();
	}
	
	// Attach the observers to the hardware
	private void attachObservers() {
		noteSlot.attach(banknoteObserver);
		noteStorage.attach(banknoteObserver);
		noteValidator.attach(banknoteObserver);
	}
	
	// Getters for the checkout total. paid total, and the banknote cart
	public BigDecimal getCheckoutTotal() {
		return this.checkoutTotal;
	}
	
	public BigDecimal getPaidTotal() {
		return this.paidTotal;
	}
	
	public ArrayList<Banknote> getBanknoteCart(){
		return this.banknoteCart;
	}

	// Add a valid banknote to the paid total and the banknote cart
	public void addValidNote(Currency currency, int value) {
		Banknote addedNote = new Banknote(currency, value);
		this.banknoteCart.add(addedNote);
		this.paidTotal.add(BigDecimal.valueOf(addedNote.getValue()));
	}
	
	
}
