package SCSSoftware;

import java.math.BigDecimal;
import java.util.Observer;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PaysWithCard implements CardReaderObserver {

	private String gettype;
	private String getnumber;
	private String getcardholder;
	private String getcvv; 
	BankSimulator bank;

	private BigDecimal transactionAmount;

	private String getTapEnabled;
	private String getchip;

	private CardReader cardReader;
	private Card pcard;
	private HashMap<String,HashMap<String,String>>paymentResult; 
	
	public BigDecimal current_total;
	public BigDecimal payment;

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

	public void cardDataRead(CardReader reader, CardData data) {
		getcardholder = data.getCardholder();
		gettype = data.getType();
		getcvv = data.getCVV();
		getnumber = data.getNumber();
	}

	public PaysWithCard(CardReader cardreader, Card card, BankSimulator bank, BigDecimal amount)
	{	
		this.bank = bank;
		this.transactionAmount= amount; 
		bank.transactionCanHappen(getcardholder, getnumber, getcvv, gettype, transactionAmount);
		String response = bank.transactionCanHappen(); // reponse is the UUID of the transaction 

		if(response != "NULL")
		{
			paymentResult= new HashMap<String,HashMap<String,String>>();
			HashMap<String, String> data = new HashMap<String, String>();  
			data.put("cardType", gettype); 
			data.put("amountPaid", transactionAmount.toString());
			paymentResult.put(response,data);  
			
		} else {
			
		}
		
	}

	private String receiptCardNum()
	{
		String[] stringParts = getnumber.split(""); 
		String returnString = stringParts[0] + stringParts[1] + stringParts[2] + stringParts[3]; 
		int numOfStars = getnumber.length() - returnString.length(); 
		for (int j = 0; j < numOfStars; j++)
			returnString += "X"; 
		return returnString; 
	}


}
