package SCSSoftwareTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.BidirectionalChannel;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.products.BarcodedProduct;

import SCSSoftware.BanknoteRunner;
import SCSSoftware.Checkout;
import SCSSoftware.PaysWithCash;
import SCSSoftware.PaysWithCoin;
import SCSSoftware.ProductCart;

public class PaysWithCashTest {
	
	private BarcodeScanner scanner;
	public BanknoteRunner banknoteRunner;
	public PaysWithCoin coinRunner;
	private ProductCart pcart;
	private Checkout checkout;
	public Numeral[] code1 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.one};
	public Numeral[] code2 = new Numeral[] {Numeral.zero, Numeral.zero, Numeral.two};
	public Barcode bc1 = new Barcode(code1); //001
	public Barcode bc2 = new Barcode(code2); //002
	public BarcodedItem item1 = new BarcodedItem(bc1, 3);
	public BarcodedItem item2 = new BarcodedItem(bc2, 4);
	public BarcodedProduct prod1 = new BarcodedProduct(bc1, "Bread", new BigDecimal(5), 3);
	public BarcodedProduct prod2 = new BarcodedProduct(bc2, "Milk", new BigDecimal(10), 4);
	private BanknoteSlot bSlot;
	private BanknoteValidator bValidator;
	private BanknoteStorageUnit bStorage;
	private BanknoteSlot bOutput;
	private CoinTray cTray;
	private CoinValidator cValidator;
	private CoinSlot cSlot;
	private int[] banknoteDenom = {5, 10, 20, 50, 100};
	private BigDecimal[] coinDenom = {BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1.00), BigDecimal.valueOf(2.00)};
	private Currency currency = Currency.getInstance("CAD");
	private PaysWithCash paysWithCash;
	
	@Before
	public void setup() {
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenom, coinDenom, 1000, 1);
		pcart = new ProductCart();
		this.scanner = station.mainScanner;
		this.bOutput = station.banknoteOutput;
		this.bSlot = station.banknoteInput;
		this.cSlot = station.coinSlot;
		this.cTray = station.coinTray;
		this.cValidator = station.coinValidator;
		this.bStorage = station.banknoteStorage;
		this.bValidator = station.banknoteValidator;
		checkout = new Checkout(scanner, pcart);
		
		coinRunner = new PaysWithCoin(cSlot, cValidator);
		banknoteRunner = new BanknoteRunner(checkout.getTotalPrice(), bSlot, bStorage, bValidator);
		
		paysWithCash = new PaysWithCash(coinRunner, banknoteRunner, station.banknoteDispensers, station.coinDispensers, bOutput, cTray);
	}

	@Test
	public void testSumCoinBanknote() throws DisabledException, OverloadException {
		Banknote note = new Banknote(Currency.getInstance("CAD"), 5);
		Coin coin = new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00));
		bSlot.accept(note);
		coinRunner.acceptCoin(coin);
		
		assert(paysWithCash.sumCoinBanknote().equals(coin.getValue().add(BigDecimal.valueOf(5))));
	}

}
