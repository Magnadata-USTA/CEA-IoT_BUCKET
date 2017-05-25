package co.edu.usta.telco.iot.rule;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class NumberOperable extends AbstractOperable{
    private final static Logger LOGGER = LoggerFactory.getLogger(NumberOperable.class);

    public NumberOperable(String strOperable) {
        if (! NumberUtils.isNumber(strOperable)) {
            throw new NumberFormatException("The left side of the argument must be a number");
        }
        super.value = NumberUtils.createDouble(strOperable);
    }

    @Override
    public boolean biggerThan(Operable pOperable) {
        if( !( pOperable instanceof NumberOperable )) {
            LOGGER.error("Argument must be a compatible operable " + pOperable);
            throw new IllegalArgumentException("Argument must be a compatible operable " + pOperable);
        }
        Double thisValue = (Double)this.getValue();
        return ( thisValue.compareTo(( (Double) pOperable.getValue())) > 0);
    }

    @Override
    public boolean lessThan(Operable pOperable) {
        if( !( pOperable instanceof NumberOperable )) {
            LOGGER.error("Argument must be a compatible operable " + pOperable);
            throw new IllegalArgumentException("Argument must be a compatible operable " + pOperable);
        }
        Double thisValue = (Double)this.getValue();
        return ( thisValue.compareTo(( (Double) pOperable.getValue())) < 0);
    }

    @Override
    public boolean biggerEquals(Operable pOperable) {
        if( !( pOperable instanceof NumberOperable )) {
            LOGGER.error("Argument must be a compatible operable " + pOperable);
            throw new IllegalArgumentException("Argument must be a compatible operable " + pOperable);
        }
        Double thisValue = (Double)this.getValue();
        return ( thisValue.compareTo(( (Double) pOperable.getValue())) >= 0);
    }

    @Override
    public boolean lessEquals(Operable pOperable) {
        if( !( pOperable instanceof NumberOperable )) {
            LOGGER.error("Argument must be a compatible operable " + pOperable);
            throw new IllegalArgumentException("Argument must be a compatible operable " + pOperable);
        }
        Double thisValue = (Double)this.getValue();
        return ( thisValue.compareTo(( (Double) pOperable.getValue())) <= 0);
    }

    @Override
    public boolean equals(Object pOperable) {
        if( !( pOperable instanceof NumberOperable )) {
            LOGGER.error("Argument must be a compatible operable " + pOperable);
            throw new IllegalArgumentException("Argument must be a compatible operable " + pOperable);
        }
        Double thisValue = (Double)this.getValue();
        return ( thisValue.equals( ((NumberOperable)pOperable).getValue() )  );
    }

    // **** TEST
/*    public static void main(String[] args) {
        System.out.println(new NumberOperable("1").equals(new NumberOperable("1.0")) == true);
    }
    */
}
