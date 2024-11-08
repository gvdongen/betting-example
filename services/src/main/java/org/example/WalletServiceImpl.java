package org.example;

import dev.restate.sdk.JsonSerdes;
import dev.restate.sdk.ObjectContext;
import dev.restate.sdk.common.StateKey;

public class WalletServiceImpl implements WalletService {

    StateKey<Long> BALANCE = StateKey.of("balance", JsonSerdes.LONG);

    @Override
    public void deposit(ObjectContext ctx, int amt){
        Long balance = ctx.get(BALANCE).orElse(0L);
        ctx.set(BALANCE, balance + amt);
    }

    @Override
    public long getBalance(ObjectContext ctx){
        return ctx.get(BALANCE).orElse(0L);
    }
}
