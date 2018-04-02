package com.speseyond.wallet.spescoin;

import com.speseyond.wallet.spescoin.controler.CoinRpcController;
import com.speseyond.wallet.spescoin.controler.DaemonController;
import com.speseyond.wallet.spescoin.controler.PropertiesLoader;
import com.speseyond.wallet.spescoin.controler.WalletRpcController;
import com.speseyond.wallet.spescoin.daemon.DaemonPortChecker;
import com.speseyond.wallet.rpc.NoParamsRpcPoller;
import com.speseyond.wallet.rpc.RpcPoller;
import com.speseyond.wallet.spescoin.rpc.SynchronizationRpcPoller;
import com.speseyond.wallet.spescoin.rpc.TransactionItemsRpcPoller;
import com.speseyond.wallet.spescoin.rpc.UnconfirmedTransactionHashesRpcPoller;
import com.speseyond.wallet.rpc.model.Addresses;
import com.speseyond.wallet.rpc.model.Status;
import com.speseyond.wallet.rpc.model.coin.BlockCount;
import com.speseyond.wallet.spescoin.util.spesUtil;
import com.speseyond.wallet.spescoin.view.TabContainer;
import com.speseyond.wallet.spescoin.view.controller.ActionController;
import com.speseyond.wallet.spescoin.view.controller.PaymentController;
import com.speseyond.wallet.spescoin.view.controller.PoolMiningController;
import com.speseyond.wallet.spescoin.view.controller.SoloMiningController;
import com.speseyond.wallet.spescoin.view.view.AddressesTabView;
import com.speseyond.wallet.spescoin.view.view.CreatePaymentTabView;
import com.speseyond.wallet.spescoin.view.view.MainFrame;
import com.speseyond.wallet.spescoin.view.view.MenuBar;
import com.speseyond.wallet.spescoin.view.view.MiningTabView;
import com.speseyond.wallet.spescoin.view.view.PaymentTabView;
import com.speseyond.wallet.spescoin.view.view.SplashWindow;
import com.speseyond.wallet.spescoin.view.view.StatusTabView;
import com.speseyond.wallet.spescoin.view.view.TransactionsTabView;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.DefaultEditorKit;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class spesWallet extends MainFrame {

	private static Logger LOGGER = Logger.getLogger(spesWallet.class);

	private static ActionController actionController;

	public static PropertiesConfiguration applicationProperties;
	public static PropertiesConfiguration walletDaemonProperties;
	public static PropertiesConfiguration coinDaemonProperties;

	private static SplashWindow loadWindow;
	private static int loadingCounter = 1;

	public static void main(String[] args) {
		try {
			for (UIManager.LookAndFeelInfo lnf : UIManager.getInstalledLookAndFeels()) {
				System.out.println(lnf.getName());
				if ("metal".equalsIgnoreCase(lnf.getName())) {
					UIManager.setLookAndFeel(lnf.getClassName());

					InputMap im = (InputMap) UIManager.get("TextField.focusInputMap");
					im.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.META_DOWN_MASK), DefaultEditorKit.copyAction);
					im.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.META_DOWN_MASK), DefaultEditorKit.pasteAction);
					im.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.META_DOWN_MASK), DefaultEditorKit.cutAction);
					im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.META_DOWN_MASK), DefaultEditorKit.selectAllAction);

					UIManager.put("Table.foreground", new ColorUIResource(Color.black));
					UIManager.put("Table.background", new ColorUIResource(spesUtil.mainColor));

					UIManager.put("OptionPane.border", new EmptyBorder(10, 10, 10, 10));
					break;
				}
			}
		} catch (Exception e) {
			/* Lazy handling this >.> */ }

		System.setProperty("user.home.forknote", "SpesCoin");

		spesUtil.copyConfigsOnRun();

		applicationProperties = new PropertiesLoader("application.config").getProperties();
		loadingFrame(applicationProperties.getString("version"));
		loadWindow.setProgress(loadingCounter++);
		walletDaemonProperties = new PropertiesLoader("coin-wallet.conf").getProperties();
		loadWindow.setProgress(loadingCounter++);
		coinDaemonProperties = new PropertiesLoader("coin.conf").getProperties();
		loadWindow.setProgress(loadingCounter++);

		LOGGER.debug(walletDaemonProperties.getInt("p2p-bind-port"));
		int daemonPort = walletDaemonProperties.getInt("p2p-bind-port");
		int daemonRpcPort = walletDaemonProperties.getInt("rpc-bind-port");
		int walletRpcPort = walletDaemonProperties.getInt("bind-port");

		LOGGER.info("Checking ports : '" + walletRpcPort + "' : '" + daemonPort + "' : '" + daemonRpcPort + "'");

		if (!spesUtil.availableForConnection(walletRpcPort) || !spesUtil.availableForConnection(daemonPort) || !spesUtil.availableForConnection(daemonRpcPort)) {
			int dialogResult = JOptionPane.showConfirmDialog(null, "A " + System.getProperty("user.home.forknote") + " dameon and/or wallet daemon is running.\nWould you like to continue with them ?",
					"Press yas to start the wallet with current daemons", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.NO_OPTION) {
				System.exit(1);
			}
		}

		LOGGER.info("Properties loaded, wallet can get started");
		String daemonExecutable = applicationProperties.getString("coin-daemon-" + spesUtil.getOperatingSystem());
		String walletExecutable = applicationProperties.getString("wallet-daemon-" + spesUtil.getOperatingSystem());
		String poolMinerExecutable = applicationProperties.getString("pool-miner-daemon-" + spesUtil.getOperatingSystem());
		spesUtil.copyDaemonsOnRun(daemonExecutable, walletExecutable, poolMinerExecutable);

		DaemonController coinDaemon = new DaemonController(applicationProperties, walletDaemonProperties, spesUtil.getOperatingSystem());
		WalletRpcController walletRpcController = new WalletRpcController(applicationProperties.getString("wallet-daemon-base-url"));
		CoinRpcController coinRpcController = new CoinRpcController(applicationProperties.getString("coin-daemon-base-url"));
		actionController = new ActionController(coinDaemon, walletRpcController, coinRpcController);
		loadWindow.setProgress(loadingCounter++);

		new spesWallet(applicationProperties, actionController);
	}

	public spesWallet(PropertiesConfiguration applicationProperties, final ActionController actionController) {
		super(applicationProperties, actionController);

		if (LOGGER.isDebugEnabled()) {
			Properties p = System.getProperties();
			Enumeration keys = p.keys();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				String value = (String) p.get(key);
				LOGGER.info(key + ": " + value);
			}
		}

		// Runtime.getRuntime().addShutdownHook(new Thread() {
		// @Override
		// public void run() {
		// actionController.exit();
		// }
		// });

		new Thread(new DaemonPortChecker(walletDaemonProperties)).start();

		LOGGER.info("Starting controllers ...");
		PaymentController paymentController = new PaymentController(actionController);
		PoolMiningController miningController = new PoolMiningController(applicationProperties, spesUtil.getOperatingSystem());
		SoloMiningController soloMiningController = new SoloMiningController(applicationProperties, walletDaemonProperties, spesUtil.getOperatingSystem());
		loadWindow.setProgress(loadingCounter++);
		LOGGER.info("Controllers started.");

		LOGGER.info("Creating tab view instances ...");
		final StatusTabView statusTabView = new StatusTabView(actionController);
		final TransactionsTabView transactionsTabView = new TransactionsTabView(actionController.getWalletRpcController().getTransactionExecutor());
		final AddressesTabView addressesTabView = new AddressesTabView(actionController);
		final PaymentTabView paymentTabView = new PaymentTabView();
		final CreatePaymentTabView createPaymentTabView = new CreatePaymentTabView(paymentController);
		final MiningTabView miningTabView = new MiningTabView(miningController, applicationProperties);
		final MiningTabView soloMiningTabView = new MiningTabView(soloMiningController, applicationProperties);

		loadWindow.setProgress(loadingCounter++);
		actionController.setMiningController(miningController);
		actionController.setSoloMiningController(soloMiningController);

		MenuBar menuBar = new MenuBar(this, walletDaemonProperties, actionController);
		setJMenuBar(menuBar);
		loadWindow.setProgress(loadingCounter++);

		List<TabContainer> containers = new ArrayList<>();
		URL splashScreenLocation = Thread.currentThread().getContextClassLoader().getResource("stats-icon.png");
		containers.add(new TabContainer<>(0, "Overview", statusTabView, true, new ImageIcon(splashScreenLocation)));
		containers.add(new TabContainer<>(1, "Addresses", addressesTabView, true));
		containers.add(new TabContainer<>(2, "Transactions", transactionsTabView, true));
		containers.add(new TabContainer<>(3, "Payments", paymentTabView, true));
		containers.add(new TabContainer<>(4, "Create payments", createPaymentTabView, true));
		containers.add(new TabContainer<>(5, "Pool Mining", miningTabView, false));
		containers.add(new TabContainer<>(6, "Solo Mining", soloMiningTabView, false));
		loadWindow.setProgress(loadingCounter++);

		setContainers(containers);

		LOGGER.info("Generating frame ...");
		loadWindow.setProgress(loadingCounter++);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Start polling and updating the views
		LOGGER.info("Starting the rpc pollers ...");
		final RpcPoller<Status> statusPoller = new NoParamsRpcPoller<>(actionController.getWalletRpcController().getStatusExecutor(), 30000);
		final SynchronizationRpcPoller<BlockCount> syncPoller = new SynchronizationRpcPoller<>(actionController.getCoinRpcController().getBlockCountExecutor(), 30000);

		statusPoller.addObserver(syncPoller);
		statusPoller.addObserver(this);
		syncPoller.addObserver(this);
		loadWindow.setProgress(loadingCounter++);

		RpcPoller<Addresses> addressesPoller = new NoParamsRpcPoller<>(actionController.getWalletRpcController().getAddressesExecutor(), 30000);
		TransactionItemsRpcPoller transactionsPoller = new TransactionItemsRpcPoller(actionController.getWalletRpcController().getTransactionsExecutor(), 10000);
		UnconfirmedTransactionHashesRpcPoller unconfirmedTransactionHashesPoller = new UnconfirmedTransactionHashesRpcPoller(actionController.getWalletRpcController().getUnconfirmedTransactionHashesExecutor(), 10000);
		actionController.getCoinRpcController().addPollers(syncPoller);

		List<RpcPoller> walletRpcPollers = new ArrayList<>();
		walletRpcPollers.add(statusPoller);
		walletRpcPollers.add(addressesPoller);
		walletRpcPollers.add(transactionsPoller);
		walletRpcPollers.add(unconfirmedTransactionHashesPoller);

		actionController.startWallet(walletRpcPollers);

		// Add observers
		transactionsPoller.addObserver(transactionsTabView);
		transactionsPoller.addObserver(statusTabView);
		transactionsPoller.addObserver(paymentTabView);

		unconfirmedTransactionHashesPoller.addObserver(statusTabView);
		unconfirmedTransactionHashesPoller.addObserver(transactionsTabView);

		statusPoller.addObserver(transactionsPoller);
		statusPoller.addObserver(statusTabView);

		addressesPoller.addObserver(statusTabView);
		addressesPoller.addObserver(addressesTabView);
		addressesPoller.addObserver(miningTabView);
		addressesPoller.addObserver(soloMiningTabView);
		addressesPoller.addObserver(paymentTabView);
		addressesPoller.addObserver(createPaymentTabView);
		addressesPoller.addObserver(transactionsTabView);
		addressesPoller.addObserver(transactionsPoller);
		addressesPoller.addObserver(unconfirmedTransactionHashesPoller);

		LOGGER.info("Rpc pollers started.");

		loadWindow.setProgress(loadingCounter++);
		loadWindow.setScreenVisible(false);
		// make sure the JFrame is visible
		this.setVisible(true);
	}

	private static void loadingFrame(String version) {
		URL splashScreenLocation = Thread.currentThread().getContextClassLoader().getResource("splash.png");
		if (splashScreenLocation != null) {
			loadWindow = new SplashWindow(new ImageIcon(splashScreenLocation), version);
		}
		loadWindow.setLocationRelativeTo(null);
		loadWindow.setProgressMax(10);
		loadWindow.setScreenVisible(true);
	}

}
