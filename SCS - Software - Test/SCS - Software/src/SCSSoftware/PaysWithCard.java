package SCSSoftware;

import java.math.BigDecimal;

import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Card;
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

	private String getTapEnabled;
	private String getchip;

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
		/*try{
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
		}*/		//commenting out for now because of compile errors
		
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

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}
}
