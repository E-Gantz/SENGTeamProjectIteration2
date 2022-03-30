package SCSSoftwareTest;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.Checkout;
import SCSSoftware.ProductCart;

public class CheckoutTest {
	private BarcodeScanner scanner;
	private ProductCart pcart;
	private Checkout checkout;
	public Numeral[] code1 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.one};
	public Numeral[] code2 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.two};
	public Barcode bc1 = new Barcode(code1); //001
	public Barcode bc2 = new Barcode(code2); //002
	public BarcodedItem item1 = new BarcodedItem(bc1, 3);
	public BarcodedItem item2 = new BarcodedItem(bc2, 4);
	public BarcodedProduct prod1 = new BarcodedProduct(bc1, "Bread", new BigDecimal(5), 3);
	public BarcodedProduct prod2 = new BarcodedProduct(bc2, "Milk", new BigDecimal(10), 4);

	@Before
	public void setUp() {
		scanner = new BarcodeScanner();
		scanner.endConfigurationPhase();
		pcart = new ProductCart();
		checkout = new Checkout(scanner, pcart);
	}

	@After
	public void tearDown(){
		scanner = null;
		pcart = null;
		checkout = null;
	}

	@Test(expected = SimulationException.class)
	public void noCheckoutWhenDisabled() {
		scanner.disable();
		checkout.enable();
	}
	
	@Test(expected = SimulationException.class)
	public void noCheckoutWithEmptyCart() {
		checkout.enable();
	}
	
	@Test
	public void scannerDisabledDuringCheckout() {
		pcart.addToCart(prod1);
		checkout.enable();
		assertTrue(scanner.isDisabled());
	}
	
	@Test
	public void checkoutPossibleWithNonEmptyCart() {
		pcart.addToCart(prod1);
		checkout.enable();
		assertTrue(checkout.getState());
	}
	
	@Test
	public void testEnable() {
		scanner.scan(item1);
		pcart.addToCart(prod1);
		scanner.enable();
		checkout.enable();
		assertTrue(checkout.getState());
	}
	
	@Test(expected = SimulationException.class)
	public void testScannerDissabled() {
		scanner.scan(item1);
		scanner.disable();
		checkout.enable();
	}
	
	@Test(expected = SimulationException.class)
	public void testEmptyCart() {
		scanner.enable();
		checkout.enable();
	}
	
	@Test
	public void cancelCheckoutGoBackToScanning() {
		pcart.addToCart(prod1);
		checkout.enable();
		checkout.disable();
		assertTrue(!(checkout.getState()) && !(scanner.isDisabled()));
	}
	
	@Test
	public void amountPaidCorrect() {
		checkout.setAmountPaid(new BigDecimal(5));
		assertTrue(new BigDecimal(5).equals(checkout.getAmountPaid()));
	}
	
	@Test
	public void correctTotalPrice() {
		pcart.addToCart(prod1);
		checkout.enable();
		assertTrue(new BigDecimal(5).equals(checkout.getTotalPrice()));
	}

}
