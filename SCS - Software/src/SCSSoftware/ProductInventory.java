package SCSSoftware;

import java.util.HashMap;
import org.lsmr.selfcheckout.products.BarcodedProduct;
import org.lsmr.selfcheckout.Barcode;

//this represents the store's inventory of products, when an item is scanned we search through this by barcode.
public class ProductInventory {
	HashMap<Barcode, BarcodedProduct> inventory;
	public ProductInventory() {
		this.inventory = new HashMap<Barcode, BarcodedProduct>();
	}
	
	public void addInventory(Barcode code, BarcodedProduct prod) {
		this.inventory.put(code, prod);
	}
	
	public BarcodedProduct getInventory(Barcode code) {
		return this.inventory.get(code);
	}
}
