package co.edu.usta.telco.iot.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Felipe on 24/11/2016.
 */
public final class SessionIdentifierGenerator {
    public static String nextSessionId() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }
}
