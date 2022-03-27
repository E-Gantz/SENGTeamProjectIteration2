package SCSSoftwareTest;
import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Card;

import SCSSoftware.BankSimulator;
import SCSSoftware.PaysWithCard;

public class BankSimulatorTest {
	private BankSimulator banksimulator;
	private Card card;
	
	private PaysWithCard payswithcard;
	@Before
	public void setup()
	{
		String name = "Jason Bourne";
		String cardnumber = "1234-0000-0000";
		String CVV = "000";
		String cardtype1 = "DEBIT";
		String cardtype2 = "CREDIT";
		BigDecimal currentbalance = new BigDecimal("500");
	}
	
	@Test
	public void cardDataTest()
	{
		
	}

}
