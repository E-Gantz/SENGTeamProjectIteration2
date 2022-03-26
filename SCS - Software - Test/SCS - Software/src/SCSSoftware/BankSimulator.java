package SCSSoftware;
import java.util.HashMap;


public class BankSimulator {

	private HashMap<String,HashMap<String,String>> db; 

	public BankSimulator()
	{
		HashMap<String,HashMap<String,String>> db = new HashMap<String,HashMap<String,String>>();
	}

	public Boolean transactionCanHappen(String customer, String number, String CVV, String cardtype, BigDecimal txnAmount)
	{		
		BigDecimal chargedValue = new BigDecimal(txnAmount);
		float intNumber = chargedValue.intValue();

		if(db.containsKey(customer) && db.get(customer).containsKey("cardNumber")
									&& db.get(customer).containsKey("CVV")
									&& db.get(customer).containsKey("cardtype")
		){
			if ( db.get("cardNumber") == number &&
				 db.get("CVV") == CVV &&
				 db.get("cardType") == cardtype
				){
					int currentBalance = getBalance(customer);
					if (currentBalance >= txnAmount)
					{
						currentBalance -= txnAmount;
						updateBalance(currentBalance, customer);
						return true; 
					}
					else 
					{
						System.out.println("Declined");
					}	
				}
		} 
		return false; 
	}

	public int getBalance(String customer)
	{
		int number;
		String currentBalance = db.get(customer).get("balance"); 
        try{
            number = Integer.parseInt(currentBalance);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		return number;
	}

	public string updateBalance(int remainingBalance, String customer)
	{
		db.get(customer).replace("balance",remainingBalance);
	}

	// just for testing 
	public void addToDatabase(String customer, String number, String CVV, String cardtype, BigDecimal balance)
	{
		if (balance < 0)
			balance = 0; 
			
		HashMap<String,String> data = new HashMap<String,String>(); 
		data.put("cardNumber", number); 
		data.put("CVV", CVV);  
		data.put("cardType",cardtype);
		data.put("balance",balamtoString()); 
		db.put(customer, data); 
	}

}
