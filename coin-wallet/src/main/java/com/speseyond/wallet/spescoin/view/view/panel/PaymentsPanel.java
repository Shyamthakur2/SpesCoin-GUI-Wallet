package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.spescoin.util.spesUtil;

import javax.swing.*;
import java.awt.*;

public class PaymentsPanel extends AbstractBorderlessJPanel {

    private JLabel numberOfPayments;
    private JLabel totalPaymentsAmount;
    private JLabel totalPaymentsLockedAmount;


    /**
     * Create the panel.
     */
    public PaymentsPanel() {
        setName("paymentsPanel");
        setForeground(SystemColor.textInactiveText);
        setBackground(spesUtil.panelColor);
        setToolTipText("This panel gives you an overview on your payments, how many has been executed and yet to be executed.");

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[]{1, 1, 1, 1, 1, 1};
        gridBagLayout.columnWeights = new double[]{0.02, 0.245, 0.03, 0.75};
        gridBagLayout.rowWeights = new double[]{0.02, 0.48, 0.02, 0.48, 0.02, 0.48};
        setLayout(gridBagLayout);

        JLabel lblBalance = new JLabel("Number of payments :");
        lblBalance.setForeground(SystemColor.activeCaptionText);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lblBalance, gbc);

        numberOfPayments = new JLabel("Loading ...");
        numberOfPayments.setForeground(spesUtil.textColor);
        numberOfPayments.setName("numberOfPayments");
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(numberOfPayments, gbc);

        JLabel lblLockedBalance = new JLabel("Total amount of payments :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(lblLockedBalance, gbc);

        totalPaymentsAmount = new JLabel("Loading ...");
        totalPaymentsAmount.setForeground(spesUtil.textColor);
        totalPaymentsAmount.setName("totalPaymentsAmount");
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(totalPaymentsAmount, gbc);

        JLabel lblUnconfirmedLockedBalance = new JLabel("Total unconfirmed payments :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(lblUnconfirmedLockedBalance, gbc);

        totalPaymentsLockedAmount = new JLabel("Loading ...");
        totalPaymentsLockedAmount.setForeground(spesUtil.textColor);
        totalPaymentsLockedAmount.setName("totalPaymentsLockedAmount");
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(totalPaymentsLockedAmount, gbc);
    }

    public JLabel getNumberOfPayments() {
        return numberOfPayments;
    }

    public JLabel getTotalPaymentsAmount() {
        return totalPaymentsAmount;
    }

    public JLabel getTotalPaymentsLockedAmount() {
        return totalPaymentsLockedAmount;
    }
}



