package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.spescoin.util.spesUtil;

import javax.swing.*;
import java.awt.*;

public class BalancePanel extends AbstractBorderlessJPanel {

    private JLabel availableBalance;
    private JLabel lockedBalance;

    /**
     * Create the panel.
     */
    public BalancePanel() {
        setBackground(spesUtil.panelColor);
        setToolTipText("This panel gives you your spendable balance and your locked balance. The locked balance needs 12 blocks to be confirmed.");
        //setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
//        Border border = getBorder();
//        Border margin = new EmptyBorder(10,10,10,10);
//        setBorder(new CompoundBorder(border, margin));

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[]{1, 1, 1, 1};
        gridBagLayout.columnWeights = new double[]{0.02, 0.245, 0.03, 0.75};
        gridBagLayout.rowWeights = new double[]{0.02, 0.48, 0.02, 0.48};
        setLayout(gridBagLayout);

        JLabel lblBalance = new JLabel("Balance :");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lblBalance, gbc);

        availableBalance = new JLabel("Loading ...");
        availableBalance.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(availableBalance, gbc);

        JLabel lblLockedBalance = new JLabel("Locked balance :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(lblLockedBalance, gbc);

        lockedBalance = new JLabel("Loading ...");
        lockedBalance.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(lockedBalance, gbc);
    }

    public JLabel getAvailableBalance() {
        return availableBalance;
    }

    public JLabel getLockedBalance() {
        return lockedBalance;
    }
}
