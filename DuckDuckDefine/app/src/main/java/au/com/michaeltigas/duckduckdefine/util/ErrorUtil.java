package au.com.michaeltigas.duckduckdefine.util;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.HttpException;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * This utility class provides static methods to handle errors
 */
public class ErrorUtil {
    // Prevent constructor initialisation
    private ErrorUtil() {
    }

    public static boolean isIoException(Throwable e) {
        return e instanceof IOException;
    }

    public static boolean isSSLHandshakeException(Throwable e) {
        return e.getClass().getName().equals(SSLHandshakeException.class.getName());
    }

    public static boolean isHttpException(Throwable e) {
        return e instanceof HttpException;
    }
}
