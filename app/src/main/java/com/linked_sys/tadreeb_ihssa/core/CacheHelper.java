package com.linked_sys.tadreeb_ihssa.core;

import com.linked_sys.tadreeb_ihssa.models.Mail;
import com.linked_sys.tadreeb_ihssa.models.Message;
import com.linked_sys.tadreeb_ihssa.models.Periods;
import com.linked_sys.tadreeb_ihssa.models.Program;
import com.linked_sys.tadreeb_ihssa.models.ProgramByPeriod;
import com.linked_sys.tadreeb_ihssa.models.TeacherProgram;
import com.linked_sys.tadreeb_ihssa.models.Teachers;

import java.util.ArrayList;

public class CacheHelper {
    private static CacheHelper mInstance = null;

    public static CacheHelper getInstance() {
        if (mInstance == null)
            mInstance = new CacheHelper();
        return mInstance;
    }

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

    //USER TOKEN
    public String token = "";

    //APP CODE
    public String appCode = "zunIhQwuD38JfFkSQBCk8gzvK5aJQaoahacqSJLhRcg=";
    public ArrayList<Periods> periodsList = new ArrayList<>();
    public ArrayList<ProgramByPeriod> programByPeriods = new ArrayList<>();
    public ArrayList<TeacherProgram> teacherDonePrograms = new ArrayList<>();
    public ArrayList<TeacherProgram> teacherAttendPrograms = new ArrayList<>();
    public ArrayList<Program> adminPrograms = new ArrayList<>();
    public ArrayList<Teachers> teachersList = new ArrayList<>();
    public String adminPeriodSelectedID = "";
    public ArrayList<Message> messages = new ArrayList<>();
    public ArrayList<Program> filteredList;
    public ArrayList<TeacherProgram> doneFilteredList;
    public ArrayList<TeacherProgram> attendFilteredList;
    public Mail body;
    public static int newMails;
}
