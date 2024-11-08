package org.example;

import dev.restate.sdk.Context;
import dev.restate.sdk.annotation.Workflow;
import org.example.types.DepositRequest;

import java.time.Duration;

@Workflow
public interface PaymentService {
    @Workflow
    boolean deposit(Context ctx, DepositRequest req);
}
