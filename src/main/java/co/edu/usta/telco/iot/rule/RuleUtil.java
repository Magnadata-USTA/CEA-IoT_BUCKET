package co.edu.usta.telco.iot.rule;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;


/**
 */
public final class RuleUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(RuleUtil.class);

    public static final String EQUALS = "{{==}}";
    public static final String DIFFERENT = "{{!=}}";

    public static final String BIGGER = "{{>}}";
    public static final String LESS = "{{<}}";

    public static final String BIGGER_EQUAL = "{{>=}}";
    public static final String LESS_EQUAL = "{{<=}}";

//    public static final String EQUALS = "";
//    public static final String DIFFERENT = "";
//
//    public static final String BIGGER = "";
//    public static final String LESS = "";
//
//    public static final String BIGGER_EQUAL = "";
//    public static final String LESS_EQUAL = "";

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    // ***** Validar que el usuario no inserte estas cadenas dentro del string de entrada {{sdljs}}
    public static String OPERATORS[] = new String[]{EQUALS, DIFFERENT, BIGGER, LESS, BIGGER_EQUAL, LESS_EQUAL};

    public static boolean evaluate(String condition, RuleDataType dataType) {

        String operator = "";

        if (StringUtils.isBlank(condition)) {
            LOGGER.error("The condition parameter is empty");
            throw new IllegalArgumentException("The condition is empty");
        }

        operator = findOperator(condition, operator);

        String[] splittedCondition = StringUtils.split(condition, operator);

        if (splittedCondition.length < 2) {
            LOGGER.error("Two elements are required to operate");
            throw new IllegalArgumentException("Two elements are required to operate");
        }

//        Operable operable = convertToOperable(splittedCondition[0]);

        switch (operator) {
            case EQUALS:
                return processEquals(dataType, splittedCondition);
            // break; not necessary as return above
            case DIFFERENT:
                return processDifferent(dataType, splittedCondition);
//            // break; not necessary as return above
//            case BIGGER:
//                return processBigger(dataType, splittedCondition);
//            // break; not necessary as return above
//            case LESS:
//                return processLess(dataType, splittedCondition);
//            // break; not necessary as return above
//            case BIGGER_EQUAL:
//                return processBiggerEqual(dataType, splittedCondition);
//            // break; not necessary as return above
//            case LESS_EQUAL:
//                return processLessEqual(dataType, splittedCondition);
//            // break; not necessary as return above

        }

        return true;
    }

    private static boolean processDifferent(RuleDataType dataType, String[] splittedCondition) {
        return !processEquals(dataType, splittedCondition);
    }

    private static boolean processEquals(RuleDataType dataType, String[] splittedCondition) {
        switch(dataType ) {
            case NUMBER:
                if (! NumberUtils.isNumber(splittedCondition[LEFT])) {
                        throw new NumberFormatException("The left side of the argument must be a number");
                }
                if (! NumberUtils.isNumber(splittedCondition[RIGHT])) {
                    throw new NumberFormatException("The right side of the argument must be a number");
                }
                return NumberUtils.createLong(splittedCondition[0]) == NumberUtils.createLong(splittedCondition[1]);

                // break; Not necessary as return above
            case DATE:
/*
                if (! isValidDate(splittedCondition[LEFT], "yyyy-MM-dd HH:mm:ss")) { // **** TODO: constant
                    throw new NumberFormatException("The date doesn't have the needed format");
                }
                if (! isValidDate(splittedCondition[RIGHT], "yyyy-MM-dd HH:mm:ss")) { // **** TODO: constant
                    throw new NumberFormatException("The date doesn't have the needed format");
                }
                return DateUtils.isSameInstant(DateUtils.parseDate(splittedCondition[LEFT]),
                        DateUtils.parseDate(splittedCondition[RIGHT]);
*/
                break;
            case STRING:
                if ( StringUtils.isBlank(splittedCondition[LEFT])) {
                    throw new NumberFormatException("The left side of the argument must have a value");
                }
                if (StringUtils.isBlank(splittedCondition[RIGHT])) {
                    throw new NumberFormatException("The right side of the argument must have a value");
                }
                return splittedCondition[LEFT].equals(splittedCondition[RIGHT]);

                // break; Not necessary as return above

        }

        return false;
    }

    private static String findOperator(String condition, String operator) {
        if (condition.contains(EQUALS)) {
            operator = EQUALS;
        } else if (condition.contains(DIFFERENT)) {
            operator = DIFFERENT;
        } else if (condition.contains(BIGGER)) {
            operator = BIGGER;
        } else if (condition.contains(LESS)) {
            operator = LESS;
        } else if (condition.contains(BIGGER_EQUAL)) {
            operator = BIGGER_EQUAL;
        } else if (condition.contains(LESS_EQUAL)) {
            operator = LESS_EQUAL;
        }
        return operator;
    }

    public static boolean isValidDate(String input, String format) {
        boolean valid = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            dateFormat.parse(input);
            valid = true;
        } catch (Exception ignore) {}

        return valid;
    }

//    private static Operable convertToOperable(String strOperable, RuleDataType dataType) {
//        switch(dataType ) {
//            case NUMBER:
//                return new NumberOperable(strOperable);
//
//            case STRING:
//                return new StringOperable(strOperable);
//
//            case DATE:
//                return new DateOperable(strOperable);
//
//        }

}
