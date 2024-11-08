package org.example;

import dev.restate.sdk.Context;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.Service;

@Service
public interface CommsService {
    @Handler
    void notifySuccess(Context ctx, String email);

    @Handler
    void notifyFailure(Context ctx, String email);
}
