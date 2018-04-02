package com.speseyond.wallet.spescoin.view.view.panel;

import com.speseyond.wallet.rpc.JsonRpcExecutor;
import com.speseyond.wallet.rpc.exception.KnownJsonRpcException;
import com.speseyond.wallet.spescoin.view.controller.ActionController;
import com.speseyond.wallet.spescoin.view.model.JComboboxItem;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;


public class ChooseAddressPanel extends AbstractAddressJPanel {

    private Logger LOGGER = Logger.getLogger(this.getClass());

    private ActionController actionController;


    public ChooseAddressPanel(ActionController actionController) {

        this.actionController = actionController;
        //construct preComponents
        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[]  { 1, 1, 1 };
        gbl.rowHeights = new int[] { 1, 1, 1 };
        gbl.columnWeights = new double[]{0.01, 0.48, 0.02};
        gbl.rowWeights = new double[] { 0.02, 0.02, 0.02 };
        setLayout(gbl);

        //construct components
        addresses = new JComboBox<>();


        GridLayout layout = new GridLayout(0 ,1);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(addresses, gbc);

        try {
            addressesList = actionController.getWalletRpcController().getAddressesExecutor().execute(JsonRpcExecutor.EMPTY_PARAMS);
            update(addressesList);
        } catch (KnownJsonRpcException e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return ((JComboboxItem)addresses.getSelectedItem()).getValue();
    }

}
