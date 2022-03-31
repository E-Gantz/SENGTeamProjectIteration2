package SCSSoftwareTest;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.lsmr.selfcheckout.Banknote;
import org.lsmr.selfcheckout.Barcode;
import org.lsmr.selfcheckout.BarcodedItem;
import org.lsmr.selfcheckout.Coin;
import org.lsmr.selfcheckout.Numeral;
import org.lsmr.selfcheckout.devices.Acceptor;
import org.lsmr.selfcheckout.devices.BanknoteSlot;
import org.lsmr.selfcheckout.devices.BanknoteStorageUnit;
import org.lsmr.selfcheckout.devices.BanknoteValidator;
import org.lsmr.selfcheckout.devices.BarcodeScanner;
import org.lsmr.selfcheckout.devices.BidirectionalChannel;
import org.lsmr.selfcheckout.devices.CoinDispenser;
import org.lsmr.selfcheckout.devices.CoinSlot;
import org.lsmr.selfcheckout.devices.CoinStorageUnit;
import org.lsmr.selfcheckout.devices.CoinTray;
import org.lsmr.selfcheckout.devices.CoinValidator;
import org.lsmr.selfcheckout.devices.DisabledException;
import org.lsmr.selfcheckout.devices.OverloadException;
import org.lsmr.selfcheckout.devices.SelfCheckoutStation;
import org.lsmr.selfcheckout.devices.UnidirectionalChannel;
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
	private CoinDispenser cDispenser;
	private CoinValidator cValidator;
	private CoinSlot cSlot;
	private int[] banknoteDenom = {5, 10, 20, 50, 100};
	private BigDecimal[] coinDenom = {BigDecimal.valueOf(0.05), BigDecimal.valueOf(0.10), BigDecimal.valueOf(0.25), BigDecimal.valueOf(1.00), BigDecimal.valueOf(2.00)};
	private Currency currency = Currency.getInstance("CAD");
	private PaysWithCash paysWithCash;
	public Map<BigDecimal, CoinDispenser> coinDispensers;
	private ArrayList<BigDecimal> coinDenominations;
	private CoinStorageUnit cStorage;
	private Coin nickel;
	private Coin dime;
	private Coin quarter;
	private Coin loonie;
	private Coin toonie;
	
	private PaysWithCoin pwc;
    
	private CoinSlot slot;
	private Currency cad = Currency.getInstance("CAD");
	
	private UnidirectionalChannel<Coin> reject;
	private Map<BigDecimal,UnidirectionalChannel<Coin>> standard;
	private UnidirectionalChannel<Coin> overflow;
	private UnidirectionalChannel<Coin> slotsink;
	
	private CoinStorageUnit storage;
	private CoinTray tray;
	private CoinDispenser coinDispenser;
	
	private Acceptor<Coin> rejAcceptor;
	private Acceptor<Coin> stanAcceptor;
	private Acceptor<Coin> overAcceptor;
	private Acceptor<Coin> sinkAcceptor;
	
	
	@Before
	public void setup() {
		Coin.DEFAULT_CURRENCY = cad;
		nickel = new Coin(BigDecimal.valueOf(0.05));
		dime = new Coin(BigDecimal.valueOf(0.10));
		quarter = new Coin(BigDecimal.valueOf(0.25));
		loonie = new Coin(BigDecimal.valueOf(1.00));
		toonie = new Coin(BigDecimal.valueOf(2.00));
		
		slot = new CoinSlot();
		coinDenominations = new ArrayList<BigDecimal>( Arrays.asList(nickel.getValue(), dime.getValue(), quarter.getValue(), loonie.getValue(), toonie.getValue()));
		cValidator = new CoinValidator(cad, coinDenominations);
		pwc = new PaysWithCoin(slot, cValidator);
		
		storage = new CoinStorageUnit(10000);
		tray = new CoinTray(100);
		storage.endConfigurationPhase();
		tray.endConfigurationPhase();
		
		coinDispensers = new HashMap<>();

        for(int i = 0; i < coinDenominations.size(); i++)
        {
            coinDispensers.put(coinDenominations.get(i), new CoinDispenser(10000));
		}
        
        for(CoinDispenser cd : coinDispensers.values())
            cd.endConfigurationPhase();
//		reject = new UnidirectionalChannel<Coin>(rejAcceptor);
//		standard = new HashMap<BigDecimal,UnidirectionalChannel<Coin>>();
//		overflow = new UnidirectionalChannel<Coin>(overAcceptor);
//		slotsink = new UnidirectionalChannel<Coin>(sinkAcceptor);
//	
//		slot.connect(slotsink);
//		for (BigDecimal denom : coinDenominations) {
//			Acceptor<Coin> acceptor = null;
//			standard.put(denom, new UnidirectionalChannel<Coin>(acceptor));
//		}
//		
//		cValidator.connect(reject, standard, overflow);
		
		
		interconnect(slot,cValidator);
		interconnect(cValidator, tray, coinDispensers, storage);
		
		cValidator.attach(pwc);
		cValidator.endConfigurationPhase();
		Coin.DEFAULT_CURRENCY = Currency.getInstance("CAD");
		nickel = new Coin(BigDecimal.valueOf(0.05));
		dime = new Coin(BigDecimal.valueOf(0.10));
		quarter = new Coin(BigDecimal.valueOf(0.25));
		loonie = new Coin(BigDecimal.valueOf(1.00));
		toonie = new Coin(BigDecimal.valueOf(2.00));
		SelfCheckoutStation station = new SelfCheckoutStation(currency, banknoteDenom, coinDenom, 1000, 1);
		pcart = new ProductCart();
		this.scanner = station.mainScanner;
		this.bOutput = station.banknoteOutput;
		this.bSlot = station.banknoteInput;
		this.cSlot = new CoinSlot();
		this.cTray = station.coinTray;
		cStorage = new CoinStorageUnit(10000);
		this.cValidator = new CoinValidator(currency, new ArrayList<BigDecimal>( Arrays.asList(nickel.getValue(), dime.getValue(), quarter.getValue(), loonie.getValue(), toonie.getValue())));
		this.bStorage = station.banknoteStorage;
		this.bValidator = station.banknoteValidator;
		this.coinDispensers = new HashMap<>();
		coinDenominations = new ArrayList<BigDecimal>( Arrays.asList(nickel.getValue(), dime.getValue(), quarter.getValue(), loonie.getValue(), toonie.getValue()));

        for(int i = 0; i < coinDenominations.size(); i++)
        {
            coinDispensers.put(coinDenominations.get(i), new CoinDispenser(10000));
		}
        
        for(CoinDispenser cd : coinDispensers.values())
            cd.endConfigurationPhase();
		checkout = new Checkout(scanner, pcart);
		
		interconnect(cSlot, cValidator);
		interconnect(cValidator, cTray, coinDispensers, cStorage);
		
		cSlot.endConfigurationPhase();
		cValidator.endConfigurationPhase();
		cTray.endConfigurationPhase();
		cStorage.endConfigurationPhase();
		
		coinRunner = new PaysWithCoin(cSlot, cValidator);
		banknoteRunner = new BanknoteRunner(checkout.getTotalPrice(), bSlot, bStorage, bValidator);
		paysWithCash = new PaysWithCash(pwc, banknoteRunner, station.banknoteDispensers, station.coinDispensers, bOutput, cTray);
	}
	
	private void interconnect(CoinValidator validator, CoinTray tray, Map<BigDecimal, CoinDispenser> dispensers,
	        CoinStorageUnit storage) {
	        UnidirectionalChannel<Coin> rejectChannel = new UnidirectionalChannel<Coin>(tray);
	        Map<BigDecimal, UnidirectionalChannel<Coin>> dispenserChannels = new HashMap<BigDecimal, UnidirectionalChannel<Coin>>();

	        for(BigDecimal denomination : dispensers.keySet()) {
	            CoinDispenser dispenser = dispensers.get(denomination);
	            dispenserChannels.put(denomination, new UnidirectionalChannel<Coin>(dispenser));
	        }

	        UnidirectionalChannel<Coin> overflowChannel = new UnidirectionalChannel<Coin>(storage);

	        validator.connect(rejectChannel, dispenserChannels, overflowChannel);
	    }
	
	private void interconnect(CoinSlot slot, CoinValidator validator) {
        UnidirectionalChannel<Coin> cc = new UnidirectionalChannel<Coin>(validator);
        slot.connect(cc);
        slot.endConfigurationPhase();
    }

	@Test
	public void testSumCoinBanknote() throws DisabledException, OverloadException {
		Banknote note = new Banknote(Currency.getInstance("CAD"), 5);
		Coin coin = new Coin(Currency.getInstance("CAD"), BigDecimal.valueOf(1.00));
		bSlot.accept(note);
		slot.accept(coin);
		BigDecimal testSet = new BigDecimal(1.00);
    	pwc.setTotal(testSet);
		assert(paysWithCash.sumCoinBanknote().doubleValue() == (testSet.add(BigDecimal.valueOf(5)).doubleValue()));
	}
}
