package my.example;

import dev.restate.sdk.Context;
import dev.restate.sdk.annotation.Handler;
import dev.restate.sdk.annotation.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class CommsService {
    private static final Logger logger =
            LogManager.getLogger(CommsService.class);

    @Handler
    public void notifySuccess(Context ctx, String email){
        logger.info("Notifying success");
    }

    @Handler
    public void notifyFailure(Context ctx, String email){
        logger.info("Notifying failure");
    }

}
