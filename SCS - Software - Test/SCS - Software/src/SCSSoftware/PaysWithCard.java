package SCSSoftware;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PaysWithCard implements CardReaderObserver {

	private String gettype;
	private String getnumber;
	private String getcardholder;
	private String getcvv; 
	BankSimulator bank;

	private BigDecimal transactionAmount;

	private CardReader cardReader;
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

	public void cardDataRead(CardReader reader, CardData data) {
		getcardholder = data.getCardholder();
		gettype = data.getType();
		getcvv = data.getCVV();
		getnumber = data.getNumber();
	}

	public PaysWithCard(CardReader cardreader, BankSimulator bank, BigDecimal amount)
	{	
		//Remember to get transaction amount somewhere
		this.bank = bank;
		this.transactionAmount= amount;
		this.cardReader = cardreader; 

		bank.transactionCanHappen(getcardholder, getnumber, getcvv, gettype, transactionAmount);
		String response = bank.transactionCanHappen(); // reponse is the UUID of the transaction 

		if(response != "NULL")
		{
			paymentResult = new HashMap<String,HashMap<String,String>>();
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

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	// cannot be used unless checkout is true

}