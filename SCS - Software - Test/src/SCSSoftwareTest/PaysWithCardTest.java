package SCSSoftwareTest;
import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.Card;
import org.lsmr.selfcheckout.ChipFailureException;
import org.lsmr.selfcheckout.MagneticStripeFailureException;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.TapFailureException;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.BankDeclinedException;
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
	private Card fakeCard;
	private String pin;
	private String name;
	private String cardnumber;
	private String CVV;
	private String carddebit;
	private String cardcredit;
	private String name2;
	private String name3;
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
		name2 = "Elon Musk";
		name3 = "Dick McDickface";
		
		BigDecimal currentbalance = new BigDecimal("500");
		tapandchip = new Card(carddebit, cardnumber, name, CVV, pin, true, true);
		notapchip = new Card(carddebit, cardnumber, name, CVV, pin, false, true);
		tapnochip = new Card(carddebit, cardnumber, name, CVV, pin, true, false);
		notapnochip = new Card(carddebit, cardnumber, name, "", pin, false, false);
		fakeCard = new Card(cardcredit, cardnumber, name2, CVV, pin, true, true);
		
		banksimulator = new BankSimulator();
		banksimulator.addToDatabase(name, cardnumber, CVV, carddebit, currentbalance);
		banksimulator.addToDatabase(name3, cardnumber, CVV, cardcredit, BigDecimal.valueOf(0));
		
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
		 Boolean inserted = false;
		 while(!inserted)
		 {
			 try 
			 { 
				 cardreader.insert(notapchip, pin);
				 inserted = true;
			 } catch (ChipFailureException e) {}
		 }
		 assertTrue(payswithcard.getPaymentResult() != null); 
	 }
	 
	 @Test 
	  public void cardTappedDataTest() throws IOException, SimulationException 
	 { 
		 Boolean inserted = false;
		 while(!inserted)
		 {
			 try 
			 { 
				 cardreader.tap(tapnochip);
				 inserted = true;
			 } catch (TapFailureException e) {}
		 }
		 assertTrue(payswithcard.getPaymentResult() != null); 
	 }
	 
	@Test
	public void swipeDataTest() throws IOException, SimulationException
	{
		 Boolean inserted = false;
		 while(!inserted)
		 {
			 try 
			 {
				 cardreader.swipe(notapnochip);
				 inserted = true;
			 } catch (MagneticStripeFailureException e) {}
			
		 } 
		assertTrue(payswithcard.getPaymentResult() != null);
	}
	
	@Test
	public void transactionIDTest() throws IOException, SimulationException
	{
		cardreader.insert(notapchip, pin);
		String id = payswithcard.receiptCardNum();
		assertTrue(id != null);
		
	}
	
	@Test 
	public void bankDeclinedTest() throws IOException, SimulationException
	{
		cardreader.insert(tapandchip, pin);
		assertTrue(banksimulator.transactionCanHappen(name3, cardnumber, CVV, cardcredit, BigDecimal.valueOf(1), true) == "NULL"); 
	}
	
	@Test 
	public void customerNotInDatabaseTest() throws IOException, SimulationException
	{
		cardreader.insert(tapandchip, pin);
		assertTrue(banksimulator.transactionCanHappen(name2, cardnumber, CVV, cardcredit, BigDecimal.valueOf(1), true) == "NULL"); 
	}
	
	@Test
	public void cardDeclinedTest() throws IOException, SimulationException
	{
		cardreader.insert(fakeCard, pin);
		
	}
	
}

