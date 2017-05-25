package co.edu.usta.telco.iot.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

/**
 */
public class StringOperable extends AbstractOperable{

    private final static Logger LOGGER = LoggerFactory.getLogger(StringOperable.class);

    public StringOperable(@NotNull String strOperable) {
        super.value = strOperable;
    }

    @Override
    public boolean biggerThan(Operable pOperable) {
        throw new UnsupportedOperationException("Method not operable currently for strings");
    }

    @Override
    public boolean lessThan(Operable pOperable) {
        throw new UnsupportedOperationException("Method not operable currently for strings");
    }

    @Override
    public boolean biggerEquals(Operable pOperable) {
        throw new UnsupportedOperationException("Method not operable currently for strings");
    }

    @Override
    public boolean lessEquals(Operable pOperable) {
        throw new UnsupportedOperationException("Method not operable currently for strings");
    }

    @Override
    public boolean equals(Object pOperable) {
        if( !( pOperable instanceof StringOperable )) {
            LOGGER.error("Argument must be a compatible operable " + pOperable);
            throw new IllegalArgumentException("Argument must be a compatible operable " + pOperable);
        }
        String thisValue = (String)this.getValue();
        return ( thisValue.equals( ((NumberOperable)pOperable).getValue() )  );
    }
}
