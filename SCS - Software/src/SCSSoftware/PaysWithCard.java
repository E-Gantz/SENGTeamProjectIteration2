package SCSSoftware;

import java.math.BigDecimal;
import java.util.HashMap;

//import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.Card.CardSwipeData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PaysWithCard implements CardReaderObserver {

	private String gettype;
	private String getnumber;
	private String getcardholder;
	private String getcvv; 
	private Checkout checkout;
	private BankSimulator bank;
	private boolean cvvrequired;

	private BigDecimal transactionAmount;


	private HashMap<String,HashMap<String,String>>paymentResult; 

	public void cardInserted(CardReader reader) {
		// IGNORE
	}

	public void cardRemoved(CardReader reader) {
		// IGNORE
	}

	public void cardTapped(CardReader reader) {
		// IGNORE
	}

	public void cardSwiped(CardReader reader) {
		// IGNORE
	}
	
	/* This method gathers customer information from the card reader and assigns it to local attributes*/

	public void cardDataRead(CardReader reader, CardData data) {
		
		if(this.checkout.getState()) {
			getcardholder = data.getCardholder();
			gettype = data.getType();
			
			if (data instanceof CardSwipeData)
			{
				getcvv = "";
				cvvrequired = false;
			} else {
				getcvv = data.getCVV();
				cvvrequired = true;
			}
				
			getnumber = data.getNumber();
			try {
				makePayment();
			} catch (BankDeclinedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	// HashMap getter method
	
	public HashMap<String,HashMap<String,String>> getPaymentResult()
	{
		return paymentResult;
	}
	
	/* The constructor initializes the banking simulator classes and retrieves what is being charged to the customer from checkout */
	public PaysWithCard(BankSimulator bank, Checkout checkout)
	{	
		//Remember to get transaction amount somewhere
		this.bank = bank;
		this.checkout = checkout;
		this.transactionAmount= this.checkout.getTotalPrice();
	}
	
	/* This method passes customer information gathered from the observer to the bank simulator class and awaits for a response 
	 * if a successful transaction occurs, selected information is then saved into a HashMap to generate receipt information
	 */
	public void makePayment() throws BankDeclinedException {
		/*
		 * response is the UUID of the transaction 
		 * (like if we were making a request to an api)
		 * */
		String response = bank.transactionCanHappen(getcardholder, getnumber, getcvv, gettype, transactionAmount, cvvrequired);

		if(response != "NULL")
		{
			paymentResult = new HashMap<String,HashMap<String,String>>();
			HashMap<String, String> data = new HashMap<String, String>();  
			data.put("cardType", gettype); 
			data.put("amountPaid", transactionAmount.toString());
			paymentResult.put(response,data);  
			
		} else {
			  throw new BankDeclinedException("Card Declined");
		}
	}
	
	/* This method replaces every digit after the first four on a customers credit card with an X for receipt printing */

	public String receiptCardNum()
	{
		String[] stringParts = getnumber.split(""); 
		String returnString = stringParts[0] + stringParts[1] + stringParts[2] + stringParts[3]; 
		int numOfStars = getnumber.length() - returnString.length(); 
		for (int j = 0; j < numOfStars; j++)
			returnString += "X"; 
		return returnString; 
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

}
