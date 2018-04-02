package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.spescoin.util.spesUtil;

import javax.swing.*;
import java.awt.*;

public class CreateAddressPanel extends AbstractBorderlessJPanel {

    private JTextField publicKey;
    private JTextField secretKey;


    /**
     * Create the panel.
     */
    public CreateAddressPanel() {
        setName("createAddressPanel");
        setForeground(SystemColor.textInactiveText);
        setBackground(spesUtil.panelColor);
        setToolTipText("This panel gives you the possibility to create an address or import spend keys.");

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[]{1, 1, 1, 1};
        gridBagLayout.columnWeights = new double[]{0.02, 0.245, 0.03, 0.75};
        gridBagLayout.rowWeights = new double[]{0.02, 0.48, 0.02, 0.48};
        setLayout(gridBagLayout);

        JLabel lblBalance = new JLabel("Public Key (optional) :");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lblBalance, gbc);

        publicKey = new JTextField();
        publicKey.setColumns(75);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(publicKey, gbc);

        JLabel lblLockedBalance = new JLabel("Secret Key (Optional) :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(lblLockedBalance, gbc);

        secretKey = new JTextField();
        secretKey.setColumns(75);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(secretKey, gbc);
    }

    public JTextField getPublicKey() {
        return publicKey;
    }

    public JTextField getSecretKey() {
        return secretKey;
    }
}



