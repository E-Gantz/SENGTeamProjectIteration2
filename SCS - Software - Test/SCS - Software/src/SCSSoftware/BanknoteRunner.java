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
	private Banknote validNote;
	
	// Paid total is the amount of currency that has been inputted and successfully validated in the machine
	// Checkout total is the amount of currency that needs to be paid
	private BigDecimal paidTotal;
	private BigDecimal checkoutTotal;
	
	// List of all banknotes that have been deposited and successfully validated
	private ArrayList<Banknote> banknoteCart;
	
	// BanknoteRunner is passed the checkout's total along with the station
	public BanknoteRunner(BigDecimal checkoutTotal) {
		
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

	// Set the current valid note as the most recent valid note given from the validator
	public void validNote(Currency currency, int value) {
		this.validNote = new Banknote(currency, value);
	}
	
	// Add the valid note to the banknote cart as well as the running total paid
	public void addValidNote() {
		paidTotal.add(BigDecimal.valueOf(validNote.getValue()));
	}
	
	// Sum banknotes in the array
	public BigDecimal sumBanknotes() {
		return this.getPaidTotal();
	}
}
