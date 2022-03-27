package SCSSoftwareTest;
import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.BankSimulator;
import SCSSoftware.Checkout;
import SCSSoftware.PaysWithCard;
import SCSSoftware.ProductCart;

public class PaysWithCardTest {
	
	private BankSimulator banksimulator;
	private PaysWithCard payswithcard;
	private CardReader cardreader;
	private Card tapandchip;
	private Card notapchip;
	private Card tapnochip;
	private Card notapnochip;
	private String pin;
	private String name;
	private String cardnumber;
	private String CVV;
	private String carddebit;
	private String cardcredit;
	private Checkout checkout;
	private BarcodeScanner barcodescanner;
	private ProductCart productcart;

	public Numeral[] code1 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.one};
	public Barcode bc1 = new Barcode(code1); 
	public BarcodedProduct prod1 = new BarcodedProduct(bc1, "Bread", new BigDecimal(5), 3);

	@Before
	public void setup()
	{
		name = "Jason Bourne";
		cardnumber = "1234-0000-0000";
		CVV = "000";
		carddebit = "DEBIT";
		cardcredit = "CREDIT";
		pin = "0000";
		
		BigDecimal currentbalance = new BigDecimal("500");
		tapandchip = new Card(carddebit, cardnumber, name, CVV, pin, true, true);
		notapchip = new Card(carddebit, cardnumber, name, CVV, pin, false, true);
		tapnochip = new Card(carddebit, cardnumber, name, CVV, pin, true, false);
		notapnochip = new Card(carddebit, cardnumber, name, "", pin, false, false);
		
		banksimulator = new BankSimulator();
		banksimulator.addToDatabase(name, cardnumber, CVV, carddebit, currentbalance);
		barcodescanner = new BarcodeScanner();
		productcart = new ProductCart();
		checkout = new Checkout(barcodescanner, productcart);
		payswithcard = new PaysWithCard(banksimulator, checkout);
		cardreader = new CardReader();
		
		cardreader.attach(payswithcard);
		cardreader.endConfigurationPhase();
		productcart.addToCart(prod1);
		cardreader.endConfigurationPhase();
		barcodescanner.endConfigurationPhase();
		checkout.enable();
		
	}
	
	
	 @Test 
	 public void cardInsertDataTest() throws IOException, SimulationException 
	 { 
		 cardreader.insert(notapchip, pin);
		 assertTrue(payswithcard.getPaymentResult() != null); 
	 }
	 
	 @Test 
	  public void cardTappedDataTest() throws IOException, SimulationException 
	 { 
		 cardreader.tap(tapnochip);
		 assertTrue(payswithcard.getPaymentResult() != null); 
	 }
	 
	@Test
	public void swipeDataTest() throws IOException, SimulationException
	{
		cardreader.swipe(notapnochip);
		assertTrue(payswithcard.getPaymentResult() != null);
	}
	
	@Test
	public void transactionIDTest() throws IOException, SimulationException
	{
		cardreader.insert(notapchip, pin);
		String id = payswithcard.receiptCardNum();
		assertTrue(id != null);
		
	}

	
}
