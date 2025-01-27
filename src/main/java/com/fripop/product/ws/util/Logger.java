package com.fripop.product.ws.util;

import org.apache.logging.log4j.message.Message;

/**
 * Custom logger.
 *
 * @since 1.0.0
 */
public class Logger {

    private org.apache.logging.log4j.Logger logger;

    /**
     * Creates a new logger to be used.
     *
     * @param clazz source class
     * @return created {@link Logger}
     */
    public static Logger getLogger(Class<?> clazz) {
        var newLogger = new Logger();
        newLogger.logger = org.apache.logging.log4j.LogManager.getLogger(clazz);
        return newLogger;
    }

    /**
     * Log info.
     *
     * @param message {@link Message}
     */
    public void info(Message message) {
        logger.info(message);
    }

    /**
     * Log info.
     *
     * @param message {@link Message}
     * @param args    arguments
     */
    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    /**
     * Log error.
     *
     * @param message {@link Message}
     */
    public void error(Message message) {
        logger.info(message);
    }

    /**
     * Log error.
     *
     * @param message {@link Message}
     * @param args    arguments
     */
    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    /**
     * Log exception.
     *
     * @param exception {@link Exception}
     */
    public void log(Exception exception) {
        logger.error(exception);
    }
}
