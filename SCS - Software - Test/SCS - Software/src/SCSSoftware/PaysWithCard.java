package SCSSoftware;

import java.math.BigDecimal;
import java.util.Observer;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PaysWithCard implements CardReaderObserver {

	private string gettype;
	private string getnumber;
	private string getcardholder;
	private string getcvv; 
	BankSimulator bank;

	public BigDecimal temp;

	private string getTapEnabled;
	private string getchip;

	private CardReader cardReader;
	private Card pcard;
	
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

	public PaysWithCard(CardReader cardreader, Card card, BankSimulator bank)
	{	
		this.bank = bank;
		bank.transactionCanHappen(getcardholder, getnumber, getcvv, gettype, temp);
		boolean response = bank.transactionCanHappen();

		if(response == true)
		{

		} 


	}

}
