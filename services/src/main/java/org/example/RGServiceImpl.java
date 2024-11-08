package org.example;

import dev.restate.sdk.JsonSerdes;
import dev.restate.sdk.ObjectContext;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.VirtualObject;
import dev.restate.sdk.common.StateKey;
import dev.restate.sdk.common.TerminalException;

public class RGServiceImpl implements RGService {

    StateKey<Long> CUSTOMER_LIMIT = StateKey.of("limit", JsonSerdes.LONG);

    @Override
    public boolean updateLimit(ObjectContext ctx, long amount){
        Long limit = ctx.get(CUSTOMER_LIMIT).orElse(10000L);

        if(limit < amount){
            return false;
        }

        ctx.set(CUSTOMER_LIMIT, limit - amount);
        return true;
    }

    @Override
    public void resetLimit(ObjectContext ctx, long amount){
        Long limit = ctx.get(CUSTOMER_LIMIT)
                .orElseThrow(() -> new TerminalException("Limit not found"));
        ctx.set(CUSTOMER_LIMIT, limit + amount);
    }
}
