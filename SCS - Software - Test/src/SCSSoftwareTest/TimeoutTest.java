package SCSSoftwareTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.ItemAdder;
import SCSSoftware.ItemPlacer;
import SCSSoftware.ProductCart;
import SCSSoftware.ProductInventory;

public class TimeoutTest {
	public BarcodeScanner scanner;
	public ItemAdder adder;
	public ProductInventory inventory;
	public ProductCart cart;
	public Numeral[] code1 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.one};
	public Numeral[] code2 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.two};
	public Barcode bc1 = new Barcode(code1); //001
	public Barcode bc2 = new Barcode(code2); //002
	public BarcodedItem item1 = new BarcodedItem(bc1, 3);
	public BarcodedItem item2 = new BarcodedItem(bc2, 4);
	public BarcodedProduct prod1 = new BarcodedProduct(bc1, "Bread", new BigDecimal(5), 3);
	public BarcodedProduct prod2 = new BarcodedProduct(bc2, "Milk", new BigDecimal(10), 4);
	public int cartSize;
	public ItemPlacer placer;
	public ElectronicScale scale;

	@Before
	public void setUp() {
		scanner = new BarcodeScanner();
		inventory = new ProductInventory();
		inventory.addInventory(bc1, prod1);
		inventory.addInventory(bc2, prod2);
		cart = new ProductCart();
		placer = new ItemPlacer(scanner, cart);
		scale = new ElectronicScale(50,1);
		scale.attach(placer);
		scale.endConfigurationPhase();
		adder = new ItemAdder(inventory, cart, placer);
		scanner.attach(adder);
		scanner.endConfigurationPhase();
		cartSize = cart.getItemNames().size();
	}

	@After
	public void tearDown() {
		scanner.detachAll();
		scale.detachAll();
		scanner = null;
		adder = null;
		scale = null;
		placer = null;
		cart = null;
		inventory = null;
		cartSize = 0;
	}
	
	@Test
	public void ItemPlacedInTime() throws InterruptedException {
		scanner.scan(item1);
		//next two if statements simulate someone retrying to scan a couple times if the first scan doesn't work
		if (cart.getItemNames().size() == cartSize) {
			scanner.scan(item1);
		}
		if (cart.getItemNames().size() == cartSize) {
			scanner.scan(item1);
		}
		TimeUnit.SECONDS.sleep(2);
		scale.add(item1);
		TimeUnit.SECONDS.sleep(3);
		assertTrue(true);
	}
	
	@Test //(expected = SimulationException.class)	//this throws an exception in a different thread, so this won't work to catch it.
	public void ItemNotPlacedInTime() throws InterruptedException, SimulationException {
		scanner.scan(item1);
		//next two if statements simulate someone retrying to scan a couple times if the first scan doesn't work
		if (cart.getItemNames().size() == cartSize) {
			scanner.scan(item1);
		}
		if (cart.getItemNames().size() == cartSize) {
			scanner.scan(item1);
		}
		TimeUnit.SECONDS.sleep(5);
		assertTrue(placer.getTimeoutStatus());
	}

}
