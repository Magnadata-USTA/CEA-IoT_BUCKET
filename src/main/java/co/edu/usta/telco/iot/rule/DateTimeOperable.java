package co.edu.usta.telco.iot.rule;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 */
public class DateTimeOperable extends AbstractOperable{

    private final static Logger LOGGER = LoggerFactory.getLogger(DateTimeOperable.class);

    public DateTimeOperable(String strOperable) {
        if (! NumberUtils.isNumber(strOperable)) {
            throw new NumberFormatException("The left side of the argument must be a number");
        }
        super.value = parseDate(strOperable);
    }

    @Override
    public boolean biggerThan(Operable operable) {
        return false;
    }

    @Override
    public boolean lessThan(Operable operable) {
        return false;
    }

    @Override
    public boolean biggerEquals(Operable operable) {
        return false;
    }

    @Override
    public boolean lessEquals(Operable operable) {
        return false;
    }

    private Date parseDate(String strOperable) {
        try {
            return DateUtils.parseDate(
                    strOperable,
                    DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());
        } catch (ParseException exception) {
            LOGGER.error("The argument is not convertable to date: " + strOperable );
            throw new IllegalArgumentException();
        }
    }

}

