package com.speseyond.wallet.rpc;

import com.speseyond.wallet.rpc.model.Address;
import com.speseyond.wallet.rpc.model.AddressBalance;
import com.speseyond.wallet.rpc.model.Addresses;
import com.speseyond.wallet.rpc.model.FusionEstimate;
import com.speseyond.wallet.rpc.model.FusionTransaction;
import com.speseyond.wallet.rpc.model.Payment;
import com.speseyond.wallet.rpc.model.SingleTransactionItem;
import com.speseyond.wallet.rpc.model.SpendKeys;
import com.speseyond.wallet.rpc.model.Status;
import com.speseyond.wallet.rpc.model.Success;
import com.speseyond.wallet.rpc.model.TransactionItems;
import com.speseyond.wallet.rpc.model.UnconfirmedTransactionHashes;
import com.speseyond.wallet.rpc.model.ViewSecretKey;

public interface WalletController {

	JsonRpcExecutor<ViewSecretKey> getViewSecretKeyExecutor();

	JsonRpcExecutor<SpendKeys> getSpendKeysExecutor();

	JsonRpcExecutor<Status> getStatusExecutor();

	JsonRpcExecutor<Address> getCreateAddressExecutor();

	JsonRpcExecutor<Success> getDeleteAddressExecutor();

	JsonRpcExecutor<AddressBalance> getBalanceExecutor();

	JsonRpcExecutor<Addresses> getAddressesExecutor();

	JsonRpcExecutor<Payment> getPaymentExecutor();

	JsonRpcExecutor<TransactionItems> getTransactionsExecutor();

	JsonRpcExecutor<SingleTransactionItem> getTransactionExecutor();

	JsonRpcExecutor<UnconfirmedTransactionHashes> getUnconfirmedTransactionHashesExecutor();

	JsonRpcExecutor<FusionEstimate> getFusionEstimateExecutor();

	JsonRpcExecutor<FusionTransaction> getFusionTransactionExecutor();

	JsonRpcExecutor<Void> getResetExecutor();

	JsonRpcExecutor<Void> getSaveExecutor();

}
