package com.speseyond.wallet.rpc;

import com.speseyond.wallet.rpc.model.coin.BlockCount;
import com.speseyond.wallet.rpc.model.coin.BlockWrapper;
import com.speseyond.wallet.rpc.model.coin.TransactionWrapper;

public interface CoinController {

	JsonRpcExecutor<BlockCount> getBlockCountExecutor();

	JsonRpcExecutor<BlockWrapper> getBlockWrapperExecutor();

	JsonRpcExecutor<TransactionWrapper> getTransactionWrapperExecutor();

}
