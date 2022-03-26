package SCSSoftware;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SimulationException;

//represents a "pay now" button that transitions the customer from "scanning mode" to "payment mode"
public class Checkout {
	boolean state;
	ProductCart pcart;
	BarcodeScanner scanner;
	private BigDecimal amountpaid;
	
	public Checkout(BarcodeScanner scanner, ProductCart pcart) {
		this.scanner = scanner;
		this.pcart = pcart;
		state = false;
	}
	
	/**
	 * Changes the state to enable checkout to occur
	 * @throws SimulationException
	 *             If the scanner is disabled and the bagging area is waiting for items.
	 * @throws SimulationException
	 *             If the cart is empty.
	 */
	public void enable() {
		if(scanner.isDisabled()) {
			throw new SimulationException("Need to place all items in bagging area before proceeding to checkout.");
		}
		
		if(pcart.getCart().isEmpty()) {
			throw new SimulationException("Cart must contain items in order to proceed to checkout.");
		}
		
		scanner.disable(); //disable scanner during payment
		state = true;
	}
	
	//disables 'checkout mode' to go back to adding items
	//this would be executed if the user cancels checkout so they can add more items.
	public void disable() {
		scanner.enable();
		state = false;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public BigDecimal getTotalPrice() {
		return this.pcart.getTotalPrice();
	}
	
	public void setAmountPaid(BigDecimal amount) {
		this.amountpaid = amount;
	}
	
	public BigDecimal getAmountPaid() {
		return this.amountpaid;
	}
	
	
	
}