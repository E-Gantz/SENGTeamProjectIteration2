package SCSSoftware;

import java.math.BigDecimal;
import java.util.*;

import org.lsmr.selfcheckout.products.BarcodedProduct;

//This represents the users virtual cart, when items are scanned they are added here to keep track of what is in their cart and how much it costs.
public class ProductCart {
	private ArrayList<BarcodedProduct> cart;
	private ArrayList<String> items;
	private BigDecimal totalPrice;
	//private CustomerOwnBag ownbag; //in my opinion the product cart doesnt need to know about the customers bags since its virtual, only needs to worry about the expected weight of the products added.
	private double totalExpectedWeight;
	
	public ProductCart() {
		cart = new ArrayList<BarcodedProduct>();
		items = new ArrayList<String>();
		totalPrice = new BigDecimal(0);
		totalExpectedWeight = 0.0;
	}
	
	public void addToCart(BarcodedProduct prod) {
		cart.add(prod);
		String nameAndPrice = prod.getDescription() + " " + "$" + prod.getPrice().toPlainString();
		items.add(nameAndPrice); // string added should look like "Milk $10" or something.
		totalPrice = totalPrice.add(prod.getPrice());
		totalExpectedWeight += prod.getExpectedWeight();
	}
	
	public void removeFromCart(BarcodedProduct prod) {
		cart.remove(prod);
		items.remove(prod.getDescription());
		totalPrice = totalPrice.subtract(prod.getPrice());
		totalExpectedWeight-=prod.getExpectedWeight();
	}
	
	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}
	
	public ArrayList<String> getItemNames(){
		return this.items;
	}
	
	public ArrayList<BarcodedProduct> getCart(){
		return this.cart;
	}
	
	public double getTotalExpectedWeight() {
		/*if(ownbag.getBagWeight() > 0) {
			return this.totalExpectedWeight - ownbag.getBagWeight();
		} else {
			return this.totalExpectedWeight;
		}*/
		return this.totalExpectedWeight;
	}
}
