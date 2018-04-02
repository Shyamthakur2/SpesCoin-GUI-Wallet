package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.spescoin.util.spesUtil;

import javax.swing.*;
import java.awt.*;

public class ServerPanel extends AbstractBorderlessJPanel {

    private JLabel peers;
    private JLabel lastBlockHash;
    private JLabel blockHeight;
    private JLabel coinsInNetwork;
    private JLabel baseReward;
    private JLabel difficulty;

    /**
     * Create the panel.
     */
    public ServerPanel() {
        setBackground(spesUtil.panelColor);
        setToolTipText("This panel gives you an overview of the network.");

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[]{1, 1, 1, 1, 1, 1};
        gridBagLayout.columnWeights = new double[]{0.02, 0.245, 0.03, 0.75};
        gridBagLayout.rowWeights = new double[]{0.02, 0.25, 0.02, 0.25, 0.02, 0.25};
        setLayout(gridBagLayout);

        JLabel lblPeers = new JLabel("Number of peers :");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(lblPeers, gbc);

        peers = new JLabel("Loading ...");
        peers.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(peers, gbc);

        JLabel lblLastBlockHash = new JLabel("Last block hash :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(lblLastBlockHash, gbc);

        lastBlockHash = new JLabel("Loading ...");
        lastBlockHash.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 3;
        add(lastBlockHash, gbc);

        JLabel lblHeight = new JLabel("Height :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(lblHeight, gbc);

        blockHeight = new JLabel("Loading ...");
        blockHeight.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 5;
        add(blockHeight, gbc);

        JLabel lblCoinsInNetwork = new JLabel("Total Coins in network :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(lblCoinsInNetwork, gbc);

        coinsInNetwork = new JLabel("Loading ...");
        coinsInNetwork.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 7;
        add(coinsInNetwork, gbc);

        JLabel lblBaseReward = new JLabel("Base reward :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 9;
        add(lblBaseReward, gbc);

        baseReward = new JLabel("Loading ...");
        baseReward.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 9;
        add(baseReward, gbc);

        JLabel lblDifficulty = new JLabel("Difficulty :");
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 1;
        gbc.gridy = 11;
        add(lblDifficulty, gbc);

        difficulty = new JLabel("Loading ...");
        difficulty.setForeground(spesUtil.textColor);
        gbc.gridx = 3;
        gbc.gridy = 11;
        add(difficulty, gbc);
    }

    public JLabel getPeers() {
        return peers;
    }

    public JLabel getLastBlockHash() {
        return lastBlockHash;
    }

    public JLabel getCoinsInNetwork() {
        return coinsInNetwork;
    }

    public JLabel getBlockHeight() {
        return blockHeight;
    }

    public JLabel getBaseReward() {
        return baseReward;
    }

    public JLabel getDifficulty() {
        return difficulty;
    }
}
