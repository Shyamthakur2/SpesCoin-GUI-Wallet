package com.speseyond.wallet.spescoin.controler;


import com.speseyond.wallet.spescoin.util.spesUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class PropertiesLoader {

    private static Logger LOGGER = Logger.getLogger(PropertiesLoader.class);

    private PropertiesConfiguration properties = new PropertiesConfiguration();


    public PropertiesLoader(String filename) {
        try {
            String location = spesUtil.getConfigRoot();

            if (new File(location + filename).exists()) {
                LOGGER.info("Loading config file : " + location + filename);
                properties.load(new FileInputStream(location + filename));
            } else {
                LOGGER.error("Couldn't load properties : " + location + filename);
            }
        } catch (ConfigurationException | IOException e) {
            LOGGER.error("WalletDaemonProperties", e);
            System.exit(1);
        }
    }

    public PropertiesConfiguration getProperties() {
        return properties;
    }
}
