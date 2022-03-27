package SCSSoftware;

import org.lsmr.selfcheckout.Item;
import org.lsmr.selfcheckout.devices.SimulationException;

public class CustomerOwnBag extends Item{
	private double ownBagWeight;
	private int numOfOwnBags;

	
	public CustomerOwnBag(double weightInGrams, double bagWeight) {
		super(weightInGrams);
		// TODO Auto-generated constructor stub
		
		/*
		 * when the customer brings own bags, it is put on a scale which should be configured so that the scale starts measuring from 0.0.
		 * */
		if(bagWeight <= 0.0)
			throw new SimulationException(new IllegalArgumentException("The bag has not been detected."));
		this.ownBagWeight = bagWeight;
	}

	
	/**
	 * The weight of the bag, in grams. 
	 * 
	 * @return The weight in grams.
	 */
	public double getBagWeight() {
		return ownBagWeight;
	}
}
