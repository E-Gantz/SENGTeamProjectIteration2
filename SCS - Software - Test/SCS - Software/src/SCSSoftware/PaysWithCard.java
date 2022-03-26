package SCSSoftware;

import java.math.BigDecimal;
import java.util.Observer;

import javax.smartcardio.Card;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PaysWithCard implements CardReaderObserver {

	private string gettype;
	private string getnumber;
	private string getcardholder;
	private string getcvv; 

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

	public PaysWithCard(CardReader cardreader, Card card)
	{
		cardreader.attach(this);
		try{
			if (type == CREDIT)
			{
				paysWithCredit();
			}
		} catch(Exception e) {
		}

		try{
			if (type == DEBIT)
			{
				paysWithDebit();
			}
		} catch(Exception e) {
		}
	}

	// public void paysWithCredit(Card card) 
	// {
	// 	Card tap = card(type, number, cardholder, getcvv, true, false); // TAP	
	// 	Card chip = card(type, number, cardholder, getcvv, false, true); // CHIP
	// }

	// public void paysWithCredit(Card card) 
	// {
	// 	card(type, number, cardholder, getcvv, true, false); // TAP
	// 	card(type, number, cardholder, getcvv, false, true); // CHIP
	// }

	public BigDecimal success()
	{
		payment = current_total;
		return payment;
	}

}

/* DO NOT DELETE: 
RNG Transaction ID
RNG card approved/declined for testing
make another class for card approval
CPSC 329 shit salting
*/
