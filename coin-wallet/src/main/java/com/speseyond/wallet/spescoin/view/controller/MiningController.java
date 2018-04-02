package com.speseyond.wallet.spescoin.view.controller;

import java.io.BufferedReader;

/**
 * Created by oliviersinnaeve on 12/09/17.
 */
public interface MiningController {
    void startMining(String pool, String port, String address, String numberOfProcessors);

    void stopMining();

    boolean isMining();

    BufferedReader getMiningOutput();
}
