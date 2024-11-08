package org.example;

import dev.restate.sdk.JsonSerdes;
import dev.restate.sdk.SharedWorkflowContext;
import dev.restate.sdk.WorkflowContext;
import dev.restate.sdk.annotation.Shared;
import dev.restate.sdk.annotation.Workflow;
import dev.restate.sdk.common.DurablePromiseKey;
import dev.restate.sdk.common.TerminalException;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.TimeoutException;

@Workflow
public class ExampleWorkflow {

    DurablePromiseKey<Boolean> GOAL_1_REACHED =
            DurablePromiseKey.of("goal_1", JsonSerdes.BOOLEAN);

    DurablePromiseKey<String> EXTERNAL_SIGNAL =
            DurablePromiseKey.of("external_signal", JsonSerdes.STRING);

    @Workflow
    public void run(WorkflowContext ctx){

        // 1. Example of letting another handler know that we reached a goal:

        // ... steps for goal 1

        // The blockTillGoal1Reached() handler waits on this:
        ctx.promiseHandle(GOAL_1_REACHED).resolve(true);

        // ... some other steps

        //-------------------------------------------------------------------------------

        // 2. Example of waiting for a signal or a timeout and then run compensations
        final Deque<Runnable> compensations = new ArrayDeque<>();
        try {
            var walletClient = WalletServiceClient.fromContext(ctx, "user123");

            // Keep track of the compensations by adding them to the deque
            compensations.add(() -> walletClient.send().withdraw(500));
            walletClient.send().deposit(500);

            // throws a timeout exception if it doesn't resolve in 2 minutes
            // gets resolved by processSignal handler below, which gets called from the outside
            ctx.promise(EXTERNAL_SIGNAL).awaitable().await(Duration.ofMinutes(2));

            ctx.run(() -> {
                // You can also do just normal future timeouts here:
                // CompletableFuture.allOf(myFutures).orTimeout(2, TimeUnit.MINUTES);
            });

            // ... next steps

        } catch (TimeoutException e) {
            // Run the compensations
            compensations.reversed().forEach(Runnable::run);
            throw new TerminalException(e.getMessage());
        }
    }

    @Shared
    public boolean blockTillGoal1Reached(SharedWorkflowContext ctx){
        return ctx.promise(GOAL_1_REACHED).awaitable().await();
    }

    @Shared
    public void processSignal(SharedWorkflowContext ctx){
        ctx.promiseHandle(EXTERNAL_SIGNAL).resolve("It happened");
    }
}
