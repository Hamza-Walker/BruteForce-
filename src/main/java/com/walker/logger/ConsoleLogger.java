package com.walker.logger;

public class ConsoleLogger implements Logger{
    @Override
    public void logError(String message) {
        System.err.println("[ERROR] " + message);

    }

    @Override
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
}
