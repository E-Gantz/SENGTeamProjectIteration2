package SCSSoftware;

import java.util.TimerTask;

import org.lsmr.selfcheckout.devices.SimulationException;

public class BaggingTimeout extends TimerTask{
	private ProductCart cart;
	private ItemPlacer placer;
	
	static int counter;
	
	public BaggingTimeout(ProductCart cart, ItemPlacer placer) {
		this.cart = cart;
		this.placer = placer;
		counter = 0;
	}

	@Override
	public void run() {
		counter++;
		if (counter == 10) {
			//System.out.println("it has been 5 seconds");
			counter = 0;
			cancel();
			placer.BagTimeout();
			throw new SimulationException("Please place your item on the scale");
		}
		else if (cart.getTotalExpectedWeight() == placer.getBagWeight()) {
			counter = 0;
			cancel();
		}
		
	}

}
