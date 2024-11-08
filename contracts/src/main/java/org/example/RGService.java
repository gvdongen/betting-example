package org.example;

import dev.restate.sdk.ObjectContext;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.VirtualObject;

@VirtualObject
public interface RGService {

    @Handler
    boolean updateLimit(ObjectContext ctx, long amount);

    @Handler
    void resetLimit(ObjectContext ctx, long amount);
}
