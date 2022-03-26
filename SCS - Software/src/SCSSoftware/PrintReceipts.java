package SCSSoftware;

import java.util.ArrayList;

import org.lsmr.selfcheckout.devices.ReceiptPrinter;

public class PrintReceipts {
	ProductCart prodCart;
	ReceiptPrinter printer;
	public ArrayList<String> items;
	
	public PrintReceipts(ProductCart prodCart,ReceiptPrinter printer) {
		this.prodCart = prodCart;
		this.printer  = printer;
	}
	
	//if input is products "Milk" and "Bread" with prices 10 and 5, output should be: "Milk $10\nBread $5\n"
	public void printReceipt() {
		String workingOn;
		items = prodCart.getItemNames();
		for (int i=0; i<items.size(); i++) {
			workingOn = items.get(i);
			for (int j=0; j<workingOn.length(); j++) {
				printer.print(workingOn.charAt(j));
			}
			printer.print('\n');
		}
		printer.cutPaper();
	}
}
