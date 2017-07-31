package com.linked_sys.hasatraining.models;


public class TeacherProgram {
    private String REF;
    private String ProgramID;
    private String ProgramName;
    private String ProgramDays;
    private String ProgramTimes;
    private String ProgramDateStrat;
    private String ProgramDateEnd;
    private String ProgramTimeStrat;
    private String ProgramLocation;
    private String RemainDays;
    private String AttendPeriodID;
    private String AttendPeriodName;
    private boolean CaseDays;
    private boolean MustAttend;
    private boolean CanPrintCertificate;

    public TeacherProgram() {
    }

    public String getREF() {
        return REF;
    }

    public void setREF(String REF) {
        this.REF = REF;
    }

    public String getProgramID() {
        return ProgramID;
    }

    public void setProgramID(String programID) {
        ProgramID = programID;
    }

    public String getProgramName() {
        return ProgramName;
    }

    public void setProgramName(String programName) {
        ProgramName = programName;
    }

    public String getProgramDays() {
        return ProgramDays;
    }

    public void setProgramDays(String programDays) {
        ProgramDays = programDays;
    }

    public String getProgramTimes() {
        return ProgramTimes;
    }

    public void setProgramTimes(String programTimes) {
        ProgramTimes = programTimes;
    }

    public String getProgramDateStrat() {
        return ProgramDateStrat;
    }

    public void setProgramDateStrat(String programDateStrat) {
        ProgramDateStrat = programDateStrat;
    }

    public String getProgramDateEnd() {
        return ProgramDateEnd;
    }

    public void setProgramDateEnd(String programDateEnd) {
        ProgramDateEnd = programDateEnd;
    }

    public String getProgramTimeStrat() {
        return ProgramTimeStrat;
    }

    public void setProgramTimeStrat(String programTimeStrat) {
        ProgramTimeStrat = programTimeStrat;
    }

    public String getProgramLocation() {
        return ProgramLocation;
    }

    public void setProgramLocation(String programLocation) {
        ProgramLocation = programLocation;
    }

    public String getRemainDays() {
        return RemainDays;
    }

    public void setRemainDays(String remainDays) {
        RemainDays = remainDays;
    }

    public String getAttendPeriodID() {
        return AttendPeriodID;
    }

    public void setAttendPeriodID(String attendPeriodID) {
        AttendPeriodID = attendPeriodID;
    }

    public String getAttendPeriodName() {
        return AttendPeriodName;
    }

    public void setAttendPeriodName(String attendPeriodName) {
        AttendPeriodName = attendPeriodName;
    }

    public boolean getCaseDays() {
        return CaseDays;
    }

    public void setCaseDays(boolean caseDays) {
        CaseDays = caseDays;
    }

    public boolean isMustAttend() {
        return MustAttend;
    }

    public void setMustAttend(boolean mustAttend) {
        MustAttend = mustAttend;
    }

    public boolean isCanPrintCertificate() {
        return CanPrintCertificate;
    }

    public void setCanPrintCertificate(boolean canPrintCertificate) {
        CanPrintCertificate = canPrintCertificate;
    }
}
