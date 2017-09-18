package com.linked_sys.tadreeb_ihssa.models;

public class StudentAbsence {
    String regRef;
    String progRef;
    boolean isAttend;
    String attendDay;
    String attendPeriod;

    public StudentAbsence(String regRef,String progRef, boolean isAttend, String attendDay, String attendPeriod) {
        this.regRef = regRef;
        this.progRef = progRef;
        this.isAttend = isAttend;
        this.attendDay = attendDay;
        this.attendPeriod = attendPeriod;
    }

    public StudentAbsence() {
    }

    public String getRegRef() {
        return regRef;
    }

    public void setRegRef(String regRef) {
        this.regRef = regRef;
    }

    public String getProgRef() {
        return progRef;
    }

    public void setProgRef(String progRef) {
        this.progRef = progRef;
    }

    public boolean isAttend() {
        return isAttend;
    }

    public void setAttend(boolean attend) {
        isAttend = attend;
    }

    public String getAttendDay() {
        return attendDay;
    }

    public void setAttendDay(String attendDay) {
        this.attendDay = attendDay;
    }

    public String getAttendPeriod() {
        return attendPeriod;
    }

    public void setAttendPeriod(String attendPeriod) {
        this.attendPeriod = attendPeriod;
    }
}
