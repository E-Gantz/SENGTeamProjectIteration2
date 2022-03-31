package SCSSoftware;

import org.lsmr.selfcheckout.Card;

public class MemberCard {
	private Card card;
	private int points;
	private String cardNum;
	
	public MemberCard(String number, String cardholder) {
		this.card = new Card("Member", number, cardholder, null, null, false, false); //create a new card with type Member, null cvv, null pin, tap not enabled, no chip
		this.cardNum = number;
	}
	
	public String getCardNumString() {
		return this.cardNum;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void addPoints(int morePoints) {
		points+=morePoints;
	}
	
	public void removePoints(int lessPoints) {
		points-=lessPoints;
	}
	
	public Card getCard() {
		return this.card;
	}

}
