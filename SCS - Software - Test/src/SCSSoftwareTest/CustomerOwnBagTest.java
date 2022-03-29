package SCSSoftwareTest;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.devices.SimulationException;

import SCSSoftware.CustomerOwnBag;
import junit.framework.Assert;

public class CustomerOwnBagTest {
	

	private CustomerOwnBag ownbag;
	private CustomerOwnBag ownbag2;
	private CustomerOwnBag ownbag3;
	

	@Test(expected = SimulationException.class)
	public void NoBagWeight() {
		ownbag = new CustomerOwnBag(1.0, -10.0);
		ownbag2 = new CustomerOwnBag(1.0, -0.1);
		ownbag3 = new CustomerOwnBag(1.0, -1.0);
	}
	
	@Test
	public void NoBagBrought() {
		ownbag = new CustomerOwnBag(10.0, 0.0);
		//ownbag2 = new CustomerOwnBag(1.0, 0.0);
		Assert.assertEquals(false, ownbag.checkOwnBag());
	}
	
	@Test
	public void BagWeightPassed() {
		ownbag = new CustomerOwnBag(1.0, 1.0);
		Assert.assertEquals( 1.0, ownbag.getBagWeight());
		ownbag2 = new CustomerOwnBag(1.0, 0.1);
		Assert.assertEquals( 0.1, ownbag2.getBagWeight());
		ownbag3 = new CustomerOwnBag(1.0, 10.0);
		Assert.assertEquals( 10.0, ownbag3.getBagWeight());
	}

}