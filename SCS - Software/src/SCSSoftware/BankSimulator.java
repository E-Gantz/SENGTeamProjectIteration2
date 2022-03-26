package SCSSoftware;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class BankSimulator {

	private HashMap<String,HashMap<String,String>> db; 

	public BankSimulator()
	{
		db = new HashMap<String,HashMap<String,String>>();
	}
	
	public String transactionCanHappen(String customer, String number, String CVV, String cardtype, BigDecimal txnAmount)
	{		
		double chargedAmount = txnAmount.doubleValue();

		if(db.containsKey(customer) && db.get(customer).containsKey("cardNumber")
									&& db.get(customer).containsKey("CVV")
									&& db.get(customer).containsKey("cardtype")
		){
			
			if ( db.get(customer).get("cardNumber") == number 
			     && db.get(customer).get("CVV") == CVV 
				 && db.get(customer).get("cardType") == cardtype
				){
					double currentBalance = getBalance(customer);
					if (currentBalance >= chargedAmount)
					{
						currentBalance -= chargedAmount;
						updateBalance(currentBalance, customer);
						UUID txId = UUID.randomUUID();
						return txId.toString(); 
					}
					else 
					{
						System.out.println("Declined");
					}	
				}
		} 
		return "NULL"; // unsuccessful / declined 
	}

	public double getBalance(String customer)
	{

		String currentBalance = db.get(customer).get("balance"); 
		return Double.parseDouble(currentBalance);

	}

	public void updateBalance(double remainingBalance, String customer)
	{
		db.get(customer).replace("balance",Double.toString(remainingBalance));
	}

	// just for testing 
	public void addToDatabase(String customer, String number, String CVV, String cardtype, BigDecimal balance)
	{
			
		HashMap<String,String> data = new HashMap<String,String>(); 
		data.put("cardNumber", number); 
		data.put("CVV", CVV);  
		data.put("cardType",cardtype);
		data.put("balance",balance.toString()); 
		db.put(customer, data); 
	}

}
