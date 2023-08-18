package com.product.server.app.command.api.exception;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class ProductServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceEventsErrorHandler.class);

    @Override
    public void onError(Exception exception, @Nonnull EventMessage<?> event, @Nonnull EventMessageHandler eventHandler) throws Exception {
        LOGGER.info("@@@ ProductServiceEventsErrorHandler ::: " + exception);
        throw exception;
    }
}
