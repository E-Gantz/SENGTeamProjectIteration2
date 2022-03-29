package SCSSoftware;

import java.util.Timer;

import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.ElectronicScaleObserver;

public class ItemPlacer implements ElectronicScaleObserver {
	private double expectedWeight;
	private double previousWeight;
	private double currentWeight;
	private ProductCart pcart;
	private BarcodeScanner scanner;
	private Boolean NotInBags;
	private Timer timer;
	private CustomerOwnBag ownbag;
	
	public ItemPlacer(BarcodeScanner scanner, ProductCart pcart) {
		this.scanner = scanner;
		this.pcart = pcart;
		this.previousWeight = 0.0;
		this.currentWeight = 0.0;
		this.NotInBags = false;
		this.timer = new Timer();
	}
	

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		//Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		//Auto-generated method stub
		
	}

	//if weight is changed then something was placed on the scale, we expect it to be the item most recently added to the cart.
	@Override
	public void weightChanged(ElectronicScale scale, double weightInGrams) throws SimulationException{
		beforePlacing();
		expectedWeight = pcart.getCart().get((pcart.getCart().size())-1).getExpectedWeight();//this gets the weight of the item most recently added to the cart.
		if(ownbag.checkOwnBag() == false)
			currentWeight = weightInGrams;
		else
			currentWeight = weightInGrams  - ownbag.getBagWeight();
		if(currentWeight == previousWeight + expectedWeight) {
			this.previousWeight = currentWeight;
			this.expectedWeight = 0.0;
			this.scanner.enable();
			this.NotInBags = false;
		}
		else {
			throw new SimulationException("Wrong item placed on scale!");
		}
	}

	@Override
	public void overload(ElectronicScale scale) {
		//Auto-generated method stub
		//put "too heavy!" message on screen or something
	}

	@Override
	public void outOfOverload(ElectronicScale scale) {
		//Auto-generated method stub
		//remove the "too heavy!" message
	}
	
	public void startTimer() {
		BaggingTimeout timeout = new BaggingTimeout(pcart, this);
		timer.schedule(timeout,50, 500); //this should run the BaggingTimeout run() method every .5 seconds.
	}
	
	public void beforePlacing() {
		ItemPlacer itmp = new ItemPlacer(scanner, pcart);
		this.ownbag = new CustomerOwnBag(1.0, itmp.getBagWeight());
	}
	
	public double getBagWeight() {
		return this.previousWeight;
	}
		
	public void BagTimeout() {
		NotInBags = true;
	}
	
	public Boolean getTimeoutStatus() {
		return this.NotInBags;
	}

}
