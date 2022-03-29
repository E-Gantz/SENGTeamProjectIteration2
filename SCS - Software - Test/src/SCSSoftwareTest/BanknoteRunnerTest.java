package SCSSoftwareTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BanknoteDispenser;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.BanknoteRunner;
import SCSSoftware.Checkout;
import SCSSoftware.ProductCart;

public class BanknoteRunnerTest {
	private BarcodeScanner scanner;
	public BanknoteRunner banknoteRunner;
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
	private BanknoteSlot bSlot;
	private BanknoteStorageUnit bStorage;
	private BanknoteValidator bValidator;
	private int[] banknoteDenom = {5, 10, 20, 50, 100};
	private Currency currency = Currency.getInstance("CAD");
	
	@Before
	public void setup() {
		scanner = new BarcodeScanner();
		scanner.endConfigurationPhase();
		pcart = new ProductCart();
		checkout = new Checkout(scanner, pcart);
		bSlot = new BanknoteSlot(false);
		bStorage = new BanknoteStorageUnit(1000);
		bValidator = new BanknoteValidator(currency, banknoteDenom);
		
		banknoteRunner = new BanknoteRunner(checkout.getTotalPrice(), bSlot, bStorage, bValidator);
	}

	@Test
	public void test() {
		assertEquals(1, 1);
	}

}
