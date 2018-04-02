package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.spescoin.util.ComponentFactory;
import com.speseyond.wallet.spescoin.util.spesUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DonationPanel extends AbstractBorderlessJPanel {

    /**
     * Create the panel.
     */
    public DonationPanel() {
        setBackground(spesUtil.panelColor);
        setToolTipText("This panel gives you opportunity to donate to charities.");
//        setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
//        Border border = getBorder();
//        Border margin = new EmptyBorder(10,10,10,10);
//        setBorder(new CompoundBorder(border, margin));

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1, 1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[]{1, 1, 1, 1, 1};
        gridBagLayout.columnWeights = new double[]{0.02, 0.84, 0.02, 0,1, 0.02};
        gridBagLayout.rowWeights = new double[]{0.02, 0.48, 0.02, 0.48, 0.02};
        setLayout(gridBagLayout);

        JLabel btcDonationAddressLabel = new JLabel("In the future this panel will give you the opportunity to donate to charities");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(btcDonationAddressLabel, gbc);

        JButton copyBtcAddress = ComponentFactory.createSubButton("Copy address");
        copyBtcAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myString = "1234";
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        });

        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(copyBtcAddress, gbc);

        JLabel litecoinDonationAddressLabel = new JLabel("");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(litecoinDonationAddressLabel, gbc);

        JButton copyLtcAddress = ComponentFactory.createSubButton("Copy address");
        copyLtcAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myString = "1234";
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        });

        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(copyLtcAddress, gbc);
    }

}
