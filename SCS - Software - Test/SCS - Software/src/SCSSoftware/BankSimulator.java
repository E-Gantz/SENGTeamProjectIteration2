package SCSSoftware;
 peterx2


 main
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class BankSimulator {

	private HashMap<String,HashMap<String,String>> db; 

	public BankSimulator()
	{
peterx2
		db = new HashMap<String,HashMap<String,String>>();

		HashMap<String,HashMap<String,String>> db = new HashMap<String,HashMap<String,String>>();
main
	}
	
	public String transactionCanHappen(String customer, String number, String CVV, String cardtype, BigDecimal txnAmount)
	{		
peterx2
		double chargedAmount = txnAmount.doubleValue();

		BigDecimal chargedValue = txnAmount;
		double chargedAmount = chargedValue.doubleValue();
main

		if(db.containsKey(customer) && db.get(customer).containsKey("cardNumber")
									&& db.get(customer).containsKey("CVV")
									&& db.get(customer).containsKey("cardtype")
		){
peterx2
			
			if ( db.get(customer).get("cardNumber") == number 
			     && db.get(customer).get("CVV") == CVV 
				 && db.get(customer).get("cardType") == cardtype

			if ( db.get("cardNumber") == number &&
				 db.get("CVV") == CVV &&
				 db.get("cardType") == cardtype
main
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
 peterx2

		String currentBalance = db.get(customer).get("balance"); 
		return Double.parseDouble(currentBalance);

	}

	public void updateBalance(double remainingBalance, String customer)
	{
		db.get(customer).replace("balance",Double.toString(remainingBalance));

		double number = 0;
		String currentBalance = db.get(customer).get("balance"); 
        try{
            number = Integer.parseInt(currentBalance);
        }
        catch (NumberFormatException ex){
            ex.printStackTrace();
        }
		return number;
	}

	public void updateBalance(int remainingBalance, String customer)
	{
		db.get(customer).replace("balance",Integer.toString(remainingBalance));
main
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

peterx2
}

}
main
