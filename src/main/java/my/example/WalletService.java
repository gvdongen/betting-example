package my.example;

import dev.restate.sdk.JsonSerdes;
import dev.restate.sdk.ObjectContext;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.Shared;
import dev.restate.sdk.annotation.VirtualObject;
import dev.restate.sdk.common.StateKey;

@VirtualObject
public class WalletService {

    StateKey<Long> BALANCE = StateKey.of("balance", JsonSerdes.LONG);

    @Handler
    public void deposit(ObjectContext ctx, int amt){
        Long balance = ctx.get(BALANCE).orElse(0L);
        ctx.set(BALANCE, balance + amt);
    }

    @Handler
    public long getBalance(ObjectContext ctx){
        return ctx.get(BALANCE).orElse(0L);
    }
}
