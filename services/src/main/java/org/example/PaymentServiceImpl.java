package org.example;

import dev.restate.sdk.Context;
import dev.restate.sdk.JsonSerdes;
import dev.restate.sdk.PreviewContext;
import dev.restate.sdk.WorkflowContext;
import dev.restate.sdk.annotation.Workflow;
import dev.restate.sdk.common.RetryPolicy;
import org.example.types.DepositRequest;

import java.time.Duration;

public class PaymentServiceImpl implements PaymentService {

    @Workflow
    public boolean deposit(WorkflowContext ctx, DepositRequest req) {
        var amt = req.getAmount();

        // 0. Create the clients to interact with the other services
        var rgClient = RGServiceClient.fromContext(ctx, req.getUserId());
        var commsClient = CommsServiceClient.fromContext(ctx);
        var walletClient = WalletServiceClient.fromContext(ctx, req.getWalletId());

        // 1. Check if the limit is passed, and update the limit
        boolean withinLimit = rgClient.updateLimit(amt).await();

        PreviewContext.run(ctx,
                RetryPolicy.defaultPolicy().setMaxAttempts(5).setMaxDelay(Duration.ofMinutes(2)),
                () -> {/*Do something*/});

        if(!withinLimit){
            commsClient.send().notifyFailure(req.getEmail());
            return false;
        }

        // 2. Charge the customer bank account and deposit the amount
        String paymentId = ctx.random().nextUUID().toString();
        boolean paymentSuccess =
                ctx.run(JsonSerdes.BOOLEAN,
                        () -> chargeCustomer(paymentId, amt, req.getPaymentMethod()));
        walletClient.send().deposit(amt);

        // 3. If the payment failed, reset the limit and send a failure notification
        if(!paymentSuccess){
            rgClient.send().resetLimit(amt);
            commsClient.send().notifyFailure(req.getEmail());
            return false;
        }

        // 4. Send a success notification and reset the limit in 7 days
        commsClient.send().notifySuccess(req.getEmail());
        rgClient.send(Duration.ofDays(7)).resetLimit(amt);
        return true;
    }

    private boolean chargeCustomer(String paymentId, int amt, String paymentMethod){
        // Charge the customer bank account
        return true;
    }
}
