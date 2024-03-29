package com.aegisql.ordinal;

import java.util.Arrays;

public class OrdinalNumber extends Number implements Ordinal<OrdinalNumber> {

    private final int maxOrdinal;
    private final OrdinalNumber[] ordinalNumbers;

    private final int number;

    public OrdinalNumber(int numberOfOrdinals) {
        this.maxOrdinal = numberOfOrdinals-1;
        this.ordinalNumbers = new OrdinalNumber[numberOfOrdinals];
        this.ordinalNumbers[0] = this;
        this.number = 0;
        for(int i = 1; i < numberOfOrdinals; i++) {
            new OrdinalNumber(i,this);
        }
    }

    private OrdinalNumber(int i, OrdinalNumber on) {
        this.maxOrdinal = on.maxOrdinal;
        this.ordinalNumbers = on.ordinalNumbers; //share
        this.ordinalNumbers[i] = this;
        this.number = i;
    }

    @Override
    public int maxOrdinal() {
        return maxOrdinal;
    }

    @Override
    public int ordinal() {
        return number;
    }

    @Override
    public OrdinalNumber[] values() {
        return ordinalNumbers.clone();
    }

    @Override
    public int intValue() {
        return number;
    }

    @Override
    public long longValue() {
        return number;
    }

    @Override
    public float floatValue() {
        return number;
    }

    @Override
    public double doubleValue() {
        return number;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
