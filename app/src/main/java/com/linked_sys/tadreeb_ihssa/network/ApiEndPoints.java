package com.linked_sys.tadreeb_ihssa.network;

public class ApiEndPoints {
    //BaseURL
    public static final String BASE_URL = "http://api.hasatadreebm.net";
    public static final String SEND_FB_TOKEN = "/API/DeviceToken/SetDeviceToken";
    public static final String REMOVE_FB_TOKEN = "/API/DeviceToken/RemoveToken";
    public static final String GET_INBOX = "/API/Message/GetAllMessages";
    public static final String GET_MESSAGE = "/API/Message/ViewMessage";

    //Motdareb --> 1
    public static final String STUDENT_SIGNIN_URL = "/API/Motadareb/Login/";
    public static final String STUDENT_PROGRAMS_URL = "/API/Motadareb/AllProgramWithStatus/";
    public static final String GET_PROGRAM_DATA = "/API/Motadareb/GetProgramdata/";
    public static final String PRINT_CERTIFICATE = "/API/Motadareb/PrintCertificate/";
    public static final String RATE_PROGRAM = "/API/Motadareb/MotadarebRateProg/";
    public static final String GET_PROGRAM_PERIODS = "/API/Motadareb/GetProgramOpenPeriods/";
    public static final String GET_PROGRAM_BY_PERIODS = "/API/Motadareb/ProgramsByPeriod/";
    public static final String GET_USER_DATA = "/API/Motadareb/Motadarebdata/";
    public static final String SEND_MSG = "/API/Motadareb/SendMail/"; //POST
    public static final String GET_CERTIFICATE_COUNT = "/API/Motadareb/CountCertificate/";
    public static final String GET_PROGRAMS_COUNT = "/API/Motadareb/CountAllProg/";
    public static final String CHECK_PROGRAM_EXIST = "/API/Motadareb/CheckProgExists/";
    public static final String SUBMIT_REGISTER = "/API/Motadareb/Register/";
    public static final String GET_PROGRAM_DATA_BY_REF_ID = "/API/Motadareb/GetProgramdataByRefId/";
    public static final String STUDENT_CERTIFICATE_URL = "//API/Motadareb/URLCertificate";
    public static final String DOWNLOAD_STUDENT_CERTIFICATE = "/API/Motadareb/DownloadCertificate";
    //Modareb --> 1
    public static final String TEACHER_SIGNIN_URL = "/API/Modareb/Login/";
    public static final String TEACHER_PROGRAMS_URL = "/API/Modareb/AllPrograms/";
    public static final String GET_TEACHER_CERTIFICATE_COUNT = "/API/Modareb/CountCertificateProg/";
    public static final String GET_TEACHER_PROGRAMS_COUNT = "/API/Modareb/CountAllProg/";
    public static final String GET_STUDENT_OF_PROGRAM = "/API/Modareb/GetAllMotadarebInProg/";
    public static final String GET_PROGRAM_STATUS = "/API/Modareb/ProgramStatus/";
    public static final String SUBMIT_ABSENCE_BY_ONE = "/API/Modareb/RecordAttendanceByOne/"; //POST
    public static final String SUBMIT_ABSENCE = "/API/Modareb/RecordAttendanceAll/"; //POST
    public static final String CHECK_ATTENDANCE = "/API/Modareb/RecordAttendanceFinish/";
    public static final String PRINT_TEACHER_CERTIFICATE = "/API/Modareb/PrintCertificate/";
    public static final String TEACHER_CERTIFICATE_URL = "/API/Modareb/URLCertificate";
    public static final String DOWNLOAD_TEACHER_CERTIFICATE = "/API/Modareb/DownloadCertificate";
    //Monaseq --> 2
    public static final String ADMIN_SIGNIN_URL = "/API/Admin/Login/";
    public static final String ADMIN_PENDING_PROGRAMS_URL = "/API/Admin/PendingPrograms/";
    public static final String ADMIN_UPDATE_PROGRAM_STATUS_URL = "/API/Admin/UpdateProgStatus/";
    public static final String ADMIN_PENDING_PROGRAMS_COUNT_URL = "/API/Admin/PendingProgramsCount/";
    public static final String ADMIN_PROGRAMS_COUNT_URL = "/API/Admin/ProgramsWithStatusCount/"; //ProgStatus = 0 --> all , ProgStatus = 1 --> accepted, ProgStatus = 2 --> not accepted
    public static final String ADMIN_PROGRAMS_WITH_STATUS_URL = "/API/Admin/ProgramsWithStatus/"; //ProgStatus = 0 --> all , ProgStatus = 1 --> accepted, ProgStatus = 2 --> not accepted
    public static final String TEACHERS_COUNT = "/API/Admin/SchoolTeacherCount/";
    public static final String TEACHERS_DATA = "/API/Admin/SchoolTeacher/";
    public static final String SEND_TECH_TICKET = "/API/Admin/SendSupportMessage"; //POST
    public static final String ADMIN_TECH_TICKET_COUNT = "/API/Admin/SupportMessageCount/";
    public static final String SEARCH_TEACHERS = "/API/Admin/SearchTeacherPrograms/";
    public static final String GET_USER_MESSAGES = "/API/Admin/SupportMessageList/";

    public static final String downloadDirectory = "Certificates";
    public static final String mainUrl = "http://api.hasatadreebm.net/Downloads/";
}

