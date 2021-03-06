package com.speseyond.wallet.spescoin.view.view;

import com.speseyond.wallet.spescoin.view.view.panel.ChooseAddressPanel;
import com.speseyond.wallet.spescoin.view.view.panel.CreateAddressPanel;
import com.speseyond.wallet.rpc.JsonRpcExecutor;
import com.speseyond.wallet.rpc.model.Address;
import com.speseyond.wallet.rpc.model.AddressInput;
import com.speseyond.wallet.rpc.model.Addresses;
import com.speseyond.wallet.rpc.model.FusionEstimate;
import com.speseyond.wallet.rpc.model.FusionEstimateInput;
import com.speseyond.wallet.rpc.model.FusionTransaction;
import com.speseyond.wallet.rpc.model.FusionTransactionInput;
import com.speseyond.wallet.rpc.model.SpendKeys;
import com.speseyond.wallet.rpc.exception.KnownJsonRpcException;
import com.speseyond.wallet.spescoin.util.spesUtil;
import com.speseyond.wallet.spescoin.view.controller.ActionController;
import com.speseyond.wallet.spescoin.view.view.panel.AboutPanel;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;


public class MenuBar extends JMenuBar {

    ActionController actionController;

    public MenuBar(final JFrame mainFrame, final PropertiesConfiguration walletProperties, ActionController controller) {
        this.actionController = controller;
        ImageIcon icon = new ImageIcon("exit.png");

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem exitMenuItem = new JMenuItem("Exit", icon);
        exitMenuItem.setMnemonic(KeyEvent.VK_E);
        exitMenuItem.setToolTipText("Exit application");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                actionController.exit();
                System.exit(0);
            }
        });

        JMenu network = new JMenu("Network");
        JMenu wallet = new JMenu("Wallet");
        JMenu help = new JMenu("Help");

        // Reset blockchain sub menu
        JMenuItem resetBlockchainMenuItem = new JMenuItem("Reset blockchain", icon);
        resetBlockchainMenuItem.setToolTipText("Reset blockchain data");
        resetBlockchainMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                actionController.resetBlockChain();
            }
        });

        // New address sub menu
        JMenuItem createNewAddressMenuItem = new JMenuItem("Create new address", icon);
        createNewAddressMenuItem.setToolTipText("Create new address");
        createNewAddressMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Address address = actionController.createAddress();
                JOptionPane.showMessageDialog(null,
                        address.getAddress(),
                        "New address - check address view",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Import address sub menu
//        JMenuItem importAddressMenuItem = new JMenuItem("Import address", icon);
//        importAddressMenuItem.setToolTipText("Import address");
//        importAddressMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ev) {
//                CreateAddressPanel createAddressPanel = new CreateAddressPanel();
//                int response = JOptionPane.showConfirmDialog(null,
//                        createAddressPanel,
//                        "Import address",
//                        JOptionPane.OK_CANCEL_OPTION,
//                        JOptionPane.QUESTION_MESSAGE,
//                        spesUtil.getIcon());
//
//                if (response == 0) {
//                    AddressInput addressInput = new AddressInput();
//                    addressInput.setPublicSpendKey(createAddressPanel.getPublicKey().getText());
//                    addressInput.setSecretSpendKey(createAddressPanel.getSecretKey().getText());
//                    Address address = actionController.importAddress(addressInput);
//                    JOptionPane.showMessageDialog(null,
//                        address.getAddress(),
//                        "Address imported - check address view",
//                        JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });

        // Import address sub menu
        JMenuItem createFusionTransactionsMenuItem = new JMenuItem("Fusion address", icon);
        createFusionTransactionsMenuItem.setToolTipText("Execute fusion transations on your address");
        createFusionTransactionsMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                ChooseAddressPanel chooseAddressPanel = new ChooseAddressPanel(actionController);
                int response = JOptionPane.showConfirmDialog(null,
                        chooseAddressPanel,
                        "Choose address to execute fusion transactions on",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        spesUtil.getIcon());

                if (response == 0) {
                    FusionEstimateInput input = new FusionEstimateInput();
                    input.setThreshold(1000000000000l);
                    input.setAddress(chooseAddressPanel.getAddress());

                    try {
                        actionController.getWalletRpcController().getPaymentExecutor().setReadTimeout(300000);
                        FusionEstimate estimate = actionController.getWalletRpcController().getFusionEstimateExecutor().execute(input.getParams());

                        if (estimate.getFusionReadyCount() > 0) {
                            FusionTransactionInput transactionInput = new FusionTransactionInput();
                            transactionInput.setAddress(chooseAddressPanel.getAddress());
                            transactionInput.setThreshold(1000000000000l);
                            transactionInput.setAnonymity(0);

                            FusionTransaction transaction = actionController.getWalletRpcController().getFusionTransactionExecutor().execute(transactionInput.getParams());

                            JOptionPane.showMessageDialog(null,
                                    "Fusion transaction hash : " + transaction.getTransactionHash(),
                                    "Fusion succeeded",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "No fusion transactions possible on address",
                                    "Fusion transactions not possible",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (KnownJsonRpcException e) {
                        JOptionPane.showMessageDialog(null,
                                "Fusion failed : " + e.getError().getMessage(),
                                "Fusion transactions failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Reset wallet sub menu
        JMenuItem resetWalletMenuItem = new JMenuItem("Reset wallet", icon);
        resetWalletMenuItem.setToolTipText("Reset wallet");
        resetWalletMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                actionController.resetWallet();
            }
        });

        // Backup wallet sub menu
        JMenuItem backupWalletMenuItem = new JMenuItem("Backup wallet", icon);
        backupWalletMenuItem.setToolTipText("Backup wallet");
        backupWalletMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                System.setProperty("apple.awt.fileDialogForDirectories", "true");

                final JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setDialogTitle("Backup wallet to directory");
                fc.setApproveButtonText("Save");
                int result = fc.showOpenDialog(mainFrame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    spesUtil.backupWallet(file.getAbsolutePath(), walletProperties.getString("container-file"), null);

                    JOptionPane.showMessageDialog(null,
                            "You wallet has been backed up to directory : " + file.getAbsolutePath(),
                            "Address backed up",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // Get spend keys sub menu
        JMenuItem spendKeysMenuItem = new JMenuItem("Spend keys", icon);
        spendKeysMenuItem.setToolTipText("Get your send keys");
        spendKeysMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Addresses addresses  = actionController.getWalletRpcController().getAddressesExecutor().execute(JsonRpcExecutor.EMPTY_PARAMS);

                    String html = "<html><body>";
                    for (String address: addresses.getAddresses()) {
                        SpendKeys keys = actionController.getSpendKeys(address);

                        html += "<h3>" + address + "</h3>";
                        html += "<textarea rows='2' cols='75'  style='overflow:auto'>" +
                                "View key (public) : " + keys.getSpendPublicKey() +
                                "&#13;&#10;" +
                                "Spend key (private) : " + keys.getSpendSecretKey() +
                                "</textarea>";
                    }
                    html += "</body></html>";

                    JOptionPane.showMessageDialog(
                            mainFrame,
                            html,
                            "Overview of spending keys",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (KnownJsonRpcException e) {
                    e.printStackTrace();
                }
            }
        });

        // Help sub menu
        JMenuItem aboutMenuItem = new JMenuItem("About the wallet");
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                JOptionPane.showMessageDialog(null,
                        new AboutPanel(),
                        "About SpesCoin Wallet",
                        JOptionPane.INFORMATION_MESSAGE,
                        spesUtil.getIcon());
            }
        });

        file.add(exitMenuItem);

        network.add(resetBlockchainMenuItem);

        wallet.add(backupWalletMenuItem);
        wallet.add(spendKeysMenuItem);
        wallet.add(resetWalletMenuItem);
        wallet.add(createNewAddressMenuItem);
        //wallet.add(importAddressMenuItem);
        wallet.add(createFusionTransactionsMenuItem);


        help.add(aboutMenuItem);

        this.add(file);
        this.add(network);
        this.add(wallet);
        this.add(help);
    }



}
