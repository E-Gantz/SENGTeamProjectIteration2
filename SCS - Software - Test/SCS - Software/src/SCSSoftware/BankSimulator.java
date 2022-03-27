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
	
	private Boolean verifyCardData(String customer, String number, String cardtype) {

		return db.get(customer).get("cardNumber") == number 
				 && db.get(customer).get("cardtype") == cardtype;
		
	}
	
	private Boolean verifyCVV(String customer, String cvv) {
		return db.get(customer).get("CVV") == cvv;
	}
	
	private String verifyCustomerTransaction(String customer, BigDecimal txnAmount) {
		double chargedAmount = txnAmount.doubleValue();
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
			return "NULL";
		}	
		
	}
	
	public String transactionCanHappen(String customer, String number, String CVV, String cardtype, BigDecimal txnAmount,
			Boolean cvvrequired)
	{		

		if (db.containsKey(customer) 
		){
			
			if (cvvrequired){
				if (verifyCardData(customer,number,cardtype) && verifyCVV(customer,CVV))
					return verifyCustomerTransaction(customer,txnAmount);
				
			}else{
				if (verifyCardData(customer,number,cardtype))
					return verifyCustomerTransaction(customer,txnAmount);
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
		data.put("cardtype",cardtype);
		data.put("balance",balance.toString()); 
		db.put(customer, data); 
	}

}
