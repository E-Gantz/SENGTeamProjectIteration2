package SCSSoftware;

import java.io.IOException;

import javax.smartcardio.Card;

import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class PaysWithCard extends CardReaderObs{
	
	private CardReader cardReader;
	private Card card_;
	private CardReaderObs cardreaderobs;
	private BigDecimal total;

	public PaysWithCard(BigDecimal total, SelfCheckoutStation station)
	{
		cardReader = cardreader;
		card_ = card;
		



	}

}
