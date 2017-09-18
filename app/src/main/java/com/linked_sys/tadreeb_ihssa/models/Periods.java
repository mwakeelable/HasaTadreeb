package com.linked_sys.tadreeb_ihssa.models;


public class Periods {
    private String periodREF;
    private String periodName;

    public String getPeriodREF() {
        return periodREF;
    }

    public String getPeriodName() {
        return periodName;
    }

    public Periods(String periodREF, String periodName) {

        this.periodREF = periodREF;
        this.periodName = periodName;
    }

    //to display object as a string in spinner
    @Override
    public String toString() {
        return periodName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Periods) {
            Periods s = (Periods) obj;
            if (s.getPeriodName().equals(periodName) && s.getPeriodREF().equals(periodREF)) return true;
        }

        return false;
    }
}
