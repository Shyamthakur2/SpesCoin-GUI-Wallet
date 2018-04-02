package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.spescoin.util.spesUtil;
import com.speseyond.wallet.spescoin.view.view.CreatePaymentTabView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class TransferPanel extends AbstractBorderlessJPanel {

    private CreatePaymentTabView parent;

    private JTextField address;
    private JTextField amount;

    private JButton deletePanelButton;

    /**
     * Create the panel.
     */
    public TransferPanel(boolean first, CreatePaymentTabView parent) {
        this.parent = parent;

        setBackground(spesUtil.mainColor);
        setToolTipText("This panel gives you your spendable balance and your locked balance. The locked balance needs 12 blocks to be confirmed.");
//        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
//        Border border = getBorder();
//        Border margin = new EmptyBorder(10,10,10,10);
//        setBorder(new CompoundBorder(border, margin));

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[]{1, 1, 1, 1};
        gridBagLayout.columnWeights = new double[]{0.02, 0.245, 0.03, 0.75};
        gridBagLayout.rowWeights = new double[]{0.02, 0.48, 0.02, 0.48};
        setLayout(gridBagLayout);

        JLabel lblBalance = new JLabel("Address :");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lblBalance, gbc);

        address = new JTextField();
        address.setColumns(75);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(address, gbc);

        JLabel lblLockedBalance = new JLabel("Amount :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(lblLockedBalance, gbc);


        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        amountFormat.setGroupingUsed(false);
        amountFormat.setMinimumFractionDigits(10);
        amountFormat.setMaximumFractionDigits(10);
        amount = new JFormattedTextField(amountFormat);
        amount.setColumns(35);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(amount, gbc);

        if (!first) {
            deletePanelButton = new JButton("Remove transfer");

            gbc.anchor = GridBagConstraints.NORTHEAST;
            gbc.gridx = 4;
            gbc.gridy = 3;
            add(deletePanelButton, gbc);
        }
    }

    public void setActionListeners() {
        deletePanelButton.addActionListener(new DeletePanelListener(parent, this));
    }

    public JTextField getAddress() {
        return address;
    }

    public JTextField getAmount() {
        return amount;
    }
}

class DeletePanelListener implements ActionListener {

    private CreatePaymentTabView parent;
    private TransferPanel panel;

    public DeletePanelListener(CreatePaymentTabView parent, TransferPanel panel) {
        this.parent = parent;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.getParent().remove(panel);
        parent.removePanel(panel);

        parent.repaint();
    }

}
