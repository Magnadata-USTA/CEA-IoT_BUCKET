package co.edu.usta.telco.iot.rule;

/**
 */
public abstract class AbstractOperable implements Operable{

    protected Object value;

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


}
