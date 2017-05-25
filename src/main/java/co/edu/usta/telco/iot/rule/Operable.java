package co.edu.usta.telco.iot.rule;

public interface Operable{
    boolean biggerThan(Operable operable);
    boolean lessThan(Operable operable);
    boolean biggerEquals(Operable operable);
    boolean lessEquals(Operable operable);

    Object getValue();
}
