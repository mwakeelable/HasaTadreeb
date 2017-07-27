package com.linked_sys.hasatraining.adapters;


public class PeriodsAdapter {
    private String periodREF;
    private String periodName;

    public String getPeriodREF() {
        return periodREF;
    }

    public String getPeriodName() {
        return periodName;
    }

    public PeriodsAdapter(String periodREF, String periodName) {

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
        if (obj instanceof PeriodsAdapter) {
            PeriodsAdapter s = (PeriodsAdapter) obj;
            if (s.getPeriodName().equals(periodName) && s.getPeriodREF().equals(periodREF)) return true;
        }

        return false;
    }
}
