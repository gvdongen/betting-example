package org.example;

import dev.restate.sdk.ObjectContext;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.VirtualObject;

@VirtualObject
public interface WalletService {

    @Handler
    void deposit(ObjectContext ctx, int amt);

    @Handler
    long getBalance(ObjectContext ctx);
}
