# spescoin-wallet
spes Coin Java gui wallet

Can be used for any coin that was created on forknote.

# Wallet modules
...

# Setup the wallet
spes Coin Java gui wallet

All we need to change are the following files in the resources bundle :

1. configs/application.config
2. configs/coin.conf
3. configs/coin-wallet.conf
4. spesWallet.java

## configs/application.config
  
Change the following properties:

| Property name | Description   | Example  |
| ------------- |:------------- |:-------- |
| coin      | The name of the coin | spescoin    |
| wallet-daemon-base-url | The url we start the daemon on (see bind-port in coin-wallet.conf) | http://0.0.0.0:9090 |
| coin-daemon-base-url | The url the coin rpc starts on | http://127.0.0.1:39156 |
| log-file-coin | The name of the coin daemon log file | spescoind.log |
| log-file-wallet | The name of the wallet daemon log file | walletd.log |
| min-width | Minimum width of the gui wallet | 1350 |
| min-height | Minimum height of the gui wallet | 775 |
| pool-pools | Comma separated list of pool urls (format: host:url) that can mine the coin | pool.myspes.org:3333, |

## configs/coin.conf

The coin.conf file is nothing more then a copy of the configs/%config_file%.conf

### Example:

> * seed-node=45.32.104.151:55491
> * seed-node=45.76.106.163:55491
> * EMISSION_SPEED_FACTOR=25
> * DIFFICULTY_TARGET=100
> * CRYPTONOTE_DISPLAY_DECIMAL_POINT=10
> * MONEY_SUPPLY=18446744073709551615
> * GENESIS_BLOCK_REWARD=1844674407370955300
> * SYNC_FROM_ZERO=1
> * DEFAULT_DUST_THRESHOLD=1000000
> * MINIMUM_FEE=1000000
> * CRYPTONOTE_MINED_MONEY_UNLOCK_WINDOW=12
> * CRYPTONOTE_BLOCK_GRANTED_FULL_REWARD_ZONE=100000
> * CRYPTONOTE_PUBLIC_ADDRESS_BASE58_PREFIX=155
> * p2p-bind-port=55491
> * rpc-bind-port=55490
> * BYTECOIN_NETWORK=861bf8ac-f267-345b-ca57-a47ea8f16e91
> * CRYPTONOTE_NAME=spescoin
> * GENESIS_COINBASE_TX_HEX=010a01ff0001a4b4e6cc99b3e6cc1902120297e9ff6c1c405c0a408a057072b301260c2be79ddb253ea4b21f477c13ca2101e7c263df1ac55603f416058d648ebd4dfc267ff7916346fb1a77939413209535
> * MAX_BLOCK_SIZE_INITIAL=100000
> * UPGRADE_HEIGHT_V2=1
> * UPGRADE_HEIGHT_V3=30

## configs/coin-wallet.conf

The coin-wallet.conf file is nothing more then a copy of the configs/%config_file%.conf + some extra properties.

See them here

| Property name | Description   | Example  |
| ------------- |:------------- |:-------- |
| daemon-port  | The port of the coin daemon rpc | 55490 |
| bind-port | The port on which we will bind the wllet rpc | 9090 |
| bind-address | This needs to be set somehow for windows (don't change for now and leave the wallet-daemon-base-url in application.config also on 0.0.0.0) | 0.0.0.0 |
| container-file | This will store the containers file location | LEAVE EMPTY (ex. container-file=) |
| container-password | This will store the containers password (this will change in future versions) | LEAVE EMPTY (ex. container-password=) |

### Example:

> * seed-node=45.32.104.151:55491
> * seed-node=45.76.106.163:55491
> * EMISSION_SPEED_FACTOR=25
> * DIFFICULTY_TARGET=100
> * CRYPTONOTE_DISPLAY_DECIMAL_POINT=10
> * MONEY_SUPPLY=18446744073709551615
> * GENESIS_BLOCK_REWARD=1844674407370955300
> * SYNC_FROM_ZERO=1
> * DEFAULT_DUST_THRESHOLD=1000000
> * MINIMUM_FEE=1000000
> * CRYPTONOTE_MINED_MONEY_UNLOCK_WINDOW=12
> * CRYPTONOTE_BLOCK_GRANTED_FULL_REWARD_ZONE=100000
> * CRYPTONOTE_PUBLIC_ADDRESS_BASE58_PREFIX=155
> * p2p-bind-port=55491
> * rpc-bind-port=55490
> * BYTECOIN_NETWORK=861bf8ac-f267-345b-ca57-a47ea8f16e91
> * CRYPTONOTE_NAME=spescoin
> * GENESIS_COINBASE_TX_HEX=010a01ff0001a4b4e6cc99b3e6cc1902120297e9ff6c1c405c0a408a057072b301260c2be79ddb253ea4b21f477c13ca2101e7c263df1ac55603f416058d648ebd4dfc267ff7916346fb1a77939413209535
> * MAX_BLOCK_SIZE_INITIAL=100000
> * UPGRADE_HEIGHT_V2=1
> * UPGRADE_HEIGHT_V3=30
> * daemon-port=55490
> * bind-port=9090
> * bind-address=0.0.0.0
>
> * container-file=
> * container-password=

## spesWallet.java

Change the coin name in this line to the cion name chosen in the application.config file. 

* System.setProperty("user.home.forknote", "spescoin");


### Your Done !
Building the wallet should get you started with your own coin.


# Customize gui (first phase)

To change the look and feel of the gui wallet you can change some files:

1. my-logo.icns : used by mac to show the app icon.
2. my-logo.ico : icon used by windows to present the app.
3. splash.png : the splash creen background file.
4. spesUtil.java

## spesUtil.java

You can change the 2 main colors of the gui there. (We will extract them and add them to the application.config as well)

This source code is slightly modified by @asm and SpesCoin.

Credits to oliviersinnaeve for releasing the B2Bcoin wallet source.

Instructions

The following command will build the project(s)
  gradlew.bat fatJar

To run the wallet
  cd coin-wallet\build\libs
  java -jar coin-wallet-all-2.0.1.jar