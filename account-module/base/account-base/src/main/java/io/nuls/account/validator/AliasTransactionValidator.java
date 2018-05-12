package io.nuls.account.validator;

import io.nuls.account.constant.AccountConstant;
import io.nuls.account.constant.AccountErrorCode;
import io.nuls.account.model.Address;
import io.nuls.account.model.Alias;
import io.nuls.account.service.AccountBaseService;
import io.nuls.account.storage.po.AliasPo;
import io.nuls.account.storage.service.AccountStorageService;
import io.nuls.account.tx.AliasTransaction;
import io.nuls.accountLedger.service.AccountLedgerService;
import io.nuls.core.tools.str.StringUtils;
import io.nuls.kernel.constant.ErrorCode;
import io.nuls.kernel.lite.annotation.Autowired;
import io.nuls.kernel.lite.annotation.Component;
import io.nuls.kernel.validate.NulsDataValidator;
import io.nuls.kernel.validate.ValidateResult;

/**
 * @author: Charlie
 * @date: 2018/5/11
 */
@Component
public class AliasTransactionValidator implements NulsDataValidator<AliasTransaction> {

    private static final AliasTransactionValidator INSTANCE = new AliasTransactionValidator();

    @Autowired
    private AccountBaseService accountBaseService;

    @Autowired
    private AccountStorageService accountStorageService;

    @Autowired
    private AccountLedgerService accountledgerService;

    private AliasTransactionValidator() {

    }

    public static AliasTransactionValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidateResult validate(AliasTransaction tx) {
        Alias alias = tx.getTxData();
        if (!Address.validAddress(alias.getAddress())) {
            return ValidateResult.getFailedResult(alias.getClass().getName(), AccountErrorCode.ADDRESS_ERROR);
        }
        if (!StringUtils.validAlias(alias.getAlias())) {
            return ValidateResult.getFailedResult(alias.getClass().getName(), AccountErrorCode.ALIAS_ERROR);
        }

       /* long aliasValue = 0;
        UtxoData utxoData = (UtxoData) tx.getCoinData();
        for (UtxoInput input : utxoData.getInputs()) {
            aliasValue += input.getFrom().getValue();
        }

        if (aliasValue < AccountConstant.ALIAS_NA.getValue() + tx.getFee().getValue()) {
            return ValidateResult.getFailedResult(ErrorCode.INVALID_INPUT);
        }

        AliasPo aliasPo = accountStorageService.getAccount(alias.getAddress()) .get(alias.getAlias());
        if (aliasPo != null) {
            return ValidateResult.getFailedResult(ErrorCode.ALIAS_EXIST);
        }*/
        return ValidateResult.getSuccessResult();
    }

}
