package com.jwd.fShop.util;

public class ExceptionMessageCreator {
    private static final String DUMB_MESSAGE = "Dumb call.";

    public static String createExceptionMessage(final String description){
        String message;
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            String className = ste.getClassName();
            String methodName = ste.getMethodName();
            StringBuilder builder = new StringBuilder();
            builder.append("In ").append(className).append(" : in ").append(methodName).append(" : ").append(description);
            message = builder.toString();
        }catch (ArrayIndexOutOfBoundsException exception){
            message = DUMB_MESSAGE;
        }
        return message;
    }
    public static String createExceptionMessage(){
        String message;
        try {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            String className = ste.getClassName();
            String methodName = ste.getMethodName();
            StringBuilder builder = new StringBuilder();
            builder.append("In ").append(className).append(" : in ").append(methodName);
            message = builder.toString();
        }catch (ArrayIndexOutOfBoundsException exception){
            message = DUMB_MESSAGE;
        }
        return message;
    }
}
