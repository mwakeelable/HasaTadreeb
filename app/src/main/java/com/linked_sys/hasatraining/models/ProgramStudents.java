package com.linked_sys.hasatraining.models;

public class ProgramStudents {
    private String regREF;
    private String studentID;
    private String studentName;
    private String studentSchool;

    public ProgramStudents() {

    }

    public String getRegREF() {
        return regREF;
    }

    public void setRegREF(String regREF) {
        this.regREF = regREF;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentSchool() {
        return studentSchool;
    }

    public void setStudentSchool(String studentSchool) {
        this.studentSchool = studentSchool;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
}
