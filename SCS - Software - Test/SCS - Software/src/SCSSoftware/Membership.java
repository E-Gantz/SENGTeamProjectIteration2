package SCSSoftware;

import java.util.HashMap;

import org.lsmr.selfcheckout.Card.CardData;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.CardReader;
import org.lsmr.selfcheckout.devices.SimulationException;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.CardReaderObserver;

public class Membership implements CardReaderObserver{
	private HashMap<String, MemberCard> members;
	private MemberCard memberCard;
	
	public Membership(HashMap<String, MemberCard> map) {
		this.members = map;
		this.memberCard = null;
	}

	
	@Override
	public void cardDataRead(CardReader reader, CardData data) {
		if (data.getType() != "Member") {
			throw new SimulationException("Please scan a membership card.");
		}
		else {
			this.memberCard = members.get(data.getNumber());
			if (memberCard == null){
				throw new SimulationException("Something went wrong with scanning your card."); //this would be the case where a valid membership card is scanned but it isnt in our database.
			}
			//maybe tell customer their card was entered and move to next stage, which is probably adding their own bags.
		}
	}
	
	public MemberCard getMemberCard() {
		return this.memberCard;
	}
	
	public void manualEntry(String number) {
		this.memberCard = members.get(number);//returns null if no card with that number exists.
		if (memberCard == null){
			throw new SimulationException("There is no member with that membership number.");
		}
		//maybe tell customer their card was entered and move to next stage, which is probably adding their own bags.
	}
	
	
	
	@Override
	public void cardSwiped(CardReader reader) {
		// this just announces something was swiped, not sure if its useful
	}
	
	
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
	} 
	
	@Override
	public void cardInserted(CardReader reader) {
		//cannot insert member cards
	}

	@Override
	public void cardRemoved(CardReader reader) {
		//member cards cant be inserted
	}

	@Override
	public void cardTapped(CardReader reader) {
		//member cards cant be tapped, they dont have a chip
	}
}
