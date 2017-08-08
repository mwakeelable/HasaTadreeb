package com.linked_sys.hasatraining.core;

import com.linked_sys.hasatraining.models.Periods;
import com.linked_sys.hasatraining.models.Program;
import com.linked_sys.hasatraining.models.ProgramByPeriod;
import com.linked_sys.hasatraining.models.TeacherProgram;

import java.util.ArrayList;

public class CacheHelper {
    private static CacheHelper mInstance = null;

    public static CacheHelper getInstance() {
        if (mInstance == null)
            mInstance = new CacheHelper();
        return mInstance;
    }
    //APP CODE
    public String appCode = "zunIhQwuD38JfFkSQBCk8gzvK5aJQaoahacqSJLhRcg=";
    public ArrayList<Periods> periodsList = new ArrayList<>();
    public ArrayList<ProgramByPeriod> programByPeriods = new ArrayList<>();
    public ArrayList<TeacherProgram> teacherDonePrograms = new ArrayList<>();
    public ArrayList<TeacherProgram> teacherAttendPrograms = new ArrayList<>();
    public ArrayList<Program> adminPrograms = new ArrayList<>();
    public String adminPeriodSelectedID = "";

}
