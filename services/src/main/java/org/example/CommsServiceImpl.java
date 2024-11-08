package org.example;

import dev.restate.sdk.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommsServiceImpl implements CommsService {
    private static final Logger logger =
            LogManager.getLogger(CommsService.class);

    @Override
    public void notifySuccess(Context ctx, String email){
        logger.info("Notifying success");
    }

    @Override
    public void notifyFailure(Context ctx, String email){
        logger.info("Notifying failure");
    }

}
