package com.speseyond.wallet.spescoin.daemon;

import com.speseyond.wallet.spescoin.util.spesUtil;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;


/**
 * It represents the properties to use when the paypal component is activated for an account.
 *
 * Created by oliviersinnaeve on 09/03/17.
 */
public class SoloMinerDaemon implements Daemon {

    private static Logger LOGGER = Logger.getLogger(SoloMinerDaemon.class);

    private Process process;

    private BufferedReader processOutBuffer;


    public SoloMinerDaemon(PropertiesConfiguration daemonProperties, PropertiesConfiguration walletProperties, String operatingSystem, String address, String numberOfProcessors) {
        LOGGER.info("Starting solo simplewallet miner daemon for OS : " + operatingSystem);

        URL baseLocation = Thread.currentThread().getContextClassLoader().getResource("spescoin-" + operatingSystem + "/");

        if (baseLocation != null) {
            String userHome = spesUtil.getUserHome();
            String location = spesUtil.getBinariesRoot();

            String daemonExecutable = daemonProperties.getString("solo-miner-daemon-" + operatingSystem);
            String container = walletProperties.getString("container-file");
            String password = walletProperties.getString("container-password");

            try {
                ProcessBuilder pb = new ProcessBuilder(
                        "binaries/" + daemonExecutable, "-config-file", userHome + "coin-wallet.conf", "--wallet-file", container, "--wallet-password", password, "--command", "start_mining", numberOfProcessors);
                if (operatingSystem.equalsIgnoreCase(spesUtil.WINDOWS)) {
                    pb = new ProcessBuilder(
                            location + "\\binaries\\" + daemonExecutable,
                            "-config-file", userHome + "coin-wallet.conf",
                            "--wallet-file", container, "--wallet-password", password,
                            "--command", "start_mining", numberOfProcessors);
                } else {
                    pb.directory(new File(location));
                }

                process = pb.start();

                InputStream processOut = process.getInputStream();
                processOutBuffer = new BufferedReader(new InputStreamReader(processOut));
            } catch (Exception ex) {
                LOGGER.error("Solo Miner Daemon failed", ex);
            }
        }
    }

    public BufferedReader getProcessOutBuffer() {
        return processOutBuffer;
    }

    @Override
    public void stop() {
        // STOP coinDaemons
        process.destroy();

        try {
            LOGGER.info("Wait for value : " + process.waitFor());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Integer processValue = null;
        while (processValue == null) {
            try {
                processValue = process.exitValue();
            } catch (IllegalThreadStateException e) {
                LOGGER.info(e.getMessage());
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                LOGGER.error("jonRcpExecutor failed", e);
            }
        }
        LOGGER.info("Killing solo miner daemon exit value : " + process.exitValue());
    }

}
