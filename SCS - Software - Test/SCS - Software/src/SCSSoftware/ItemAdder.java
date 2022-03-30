package SCSSoftware;

import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.devices.AbstractDevice;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.observers.AbstractDeviceObserver;
import org.lsmr.selfcheckout.devices.observers.BarcodeScannerObserver;
import org.lsmr.selfcheckout.products.BarcodedProduct;

public class ItemAdder implements BarcodeScannerObserver{
	public ProductInventory productInventory;
	public ProductCart cart;
	public ItemPlacer placer;
	
	public ItemAdder(ProductInventory inventory, ProductCart cart, ItemPlacer placer) {
		this.productInventory = inventory;
		this.cart = cart;
		this.placer = placer;
	}

	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// Auto-generated method stub
		//maybe put 'you may continue scanning' on the screen or just remove the disabled screen
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// Auto-generated method stub
		//this could be where we put the 'please add item to bagging area' screen on the gui
		//only do this if its disabled because an item has not been bagged, rather than because the customer wants to checkout.
	}

	@Override
	public void barcodeScanned(BarcodeScanner barcodeScanner, Barcode barcode) {
		BarcodedProduct scannedProduct = productInventory.getInventory(barcode);
		cart.addToCart(scannedProduct);
		barcodeScanner.disable();
		placer.startTimer();
	}

}
