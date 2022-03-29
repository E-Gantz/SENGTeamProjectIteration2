package SCSSoftware;

import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SimulationException;

public class CustomerOwnBag extends Item{
	
	private double ownBagWeight;
	private boolean broughtBag;

	public CustomerOwnBag(double weightInGrams, double bagWeight) {
		super(weightInGrams);
		// TODO Auto-generated constructor stub
		if(bagWeight < 0.0) {
			throw new SimulationException("Error with the scale");
		} else if(bagWeight == 0.0) {
			this.broughtBag = false;
		} else {
			this.ownBagWeight = bagWeight;
			this.broughtBag = true;	
		}
	}

	/**
	 * The weight of the bag, in grams. 
	 * 
	 * @return The weight in grams.
	 */
	public double getBagWeight() {
		return ownBagWeight;
	}
	
	/**
	 * Check if customer brought their own bag. 
	 * 
	 * @return boolean value.
	 */
	public boolean checkOwnBag() {
		return broughtBag;
	}
	
	
	public void setBroughtBag(boolean broughtBag) {
		this.broughtBag = broughtBag;
	}

}
