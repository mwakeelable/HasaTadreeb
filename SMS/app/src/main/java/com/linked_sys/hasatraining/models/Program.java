package com.linked_sys.hasatraining.models;

public class Program {
    private String RegREF;
    private String ProgramREF;
    private String ProgramID;
    private String ProgramName;
    private String ProgramDate;
    private String ProgramTime;
    private String ProgramLocation;
    private String ProgramStatus;
    private boolean MustRate;
    private boolean CanPrintCertificate;

    public Program() {
    }

    public String getRegREF() {
        return RegREF;
    }

    public void setRegREF(String regREF) {
        RegREF = regREF;
    }

    public String getProgramREF() {
        return ProgramREF;
    }

    public void setProgramREF(String programREF) {
        ProgramREF = programREF;
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

    public String getProgramDate() {
        return ProgramDate;
    }

    public void setProgramDate(String programDate) {
        ProgramDate = programDate;
    }

    public String getProgramTime() {
        return ProgramTime;
    }

    public void setProgramTime(String programTime) {
        ProgramTime = programTime;
    }

    public String getProgramLocation() {
        return ProgramLocation;
    }

    public void setProgramLocation(String programLocation) {
        ProgramLocation = programLocation;
    }

    public String getProgramStatus() {
        return ProgramStatus;
    }

    public void setProgramStatus(String programStatus) {
        ProgramStatus = programStatus;
    }

    public boolean isMustRate() {
        return MustRate;
    }

    public void setMustRate(boolean mustRate) {
        MustRate = mustRate;
    }

    public boolean isCanPrintCertificate() {
        return CanPrintCertificate;
    }

    public void setCanPrintCertificate(boolean canPrintCertificate) {
        CanPrintCertificate = canPrintCertificate;
    }
}
