package SCSSoftwareTest;

import static org.junit.Assert.*;
import org.junit.*;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.ElectronicScale;
import org.lsmr.selfcheckout.devices.ReceiptPrinter;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.ItemAdder;
import SCSSoftware.ItemPlacer;
import SCSSoftware.PrintReceipts;
import SCSSoftware.ProductCart;
import SCSSoftware.ProductInventory;

import java.math.BigDecimal;

public class ReceiptTest {
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
	public ItemPlacer placer;
	public ElectronicScale scale;
	public int cartSize;
	
	@Test
	public void OneItemReceipt() {
		ReceiptPrinter printer = new ReceiptPrinter();
		cart = new ProductCart();
		cart.addToCart(prod1);
		printer.addInk(100);
		printer.addPaper(100);
		printer.endConfigurationPhase();
		PrintReceipts receiptPrintout = new PrintReceipts(cart, printer);
		receiptPrintout.printReceipt();
		String returnedReceipt = printer.removeReceipt();
		assertEquals(returnedReceipt, "Bread $5\n");
	}
	
	@Test
	public void TwoItemReceipt() {
		ReceiptPrinter printer = new ReceiptPrinter();
		cart = new ProductCart();
		cart.addToCart(prod1);
		cart.addToCart(prod2);
		printer.addInk(100);
		printer.addPaper(100);
		printer.endConfigurationPhase();
		PrintReceipts receiptPrintout = new PrintReceipts(cart, printer);
		receiptPrintout.printReceipt();
		String returnedReceipt = printer.removeReceipt();
		assertEquals(returnedReceipt, "Bread $5\nMilk $10\n");
	}
}
