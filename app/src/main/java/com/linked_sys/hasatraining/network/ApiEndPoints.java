package com.linked_sys.hasatraining.network;

public class ApiEndPoints {
//    http://api.hasatadreebm.net
//    http://tadreeb.linked-sys.com

    public static final String BASE_URL = "http://api.hasatadreebm.net";

    public static final String STUDENT_SIGNIN_URL = "/API/Motadareb/Login/";
    public static final String TEACHER_SIGNIN_URL = "/API/Modareb/Login/";

    public static final String STUDENT_PROGRAMS_URL = "/API/Motadareb/AllProgramWithStatus/";
    public static final String TEACHER_PROGRAMS_URL = "/API/Modareb/AllPrograms/";

    public static final String GET_PROGRAM_DATA = "/API/Motadareb/GetProgramdata/";
    public static final String PRINT_CERTIFICATE = "/API/Motadareb/PrintCertificate/";
    public static final String RATE_PROGRAM = "/API/Motadareb/MotadarebRateProg/";
    public static final String GET_PROGRAM_PERIODS = "/API/Motadareb/GetProgramOpenPeriods/";
    public static final String GET_PROGRAM_BY_PERIODS = "/API/Motadareb/ProgramsByPeriod/";
    public static final String GET_USER_DATA = "/API/Motadareb/Motadarebdata/";
    public static final String SEND_MSG = "/API/Motadareb/SendMail/"; //POST


    public static final String GET_CERTIFICATE_COUNT = "/API/Motadareb/CountCertificate/";
    public static final String GET_TEACHER_CERTIFICATE_COUNT = "/API/Modareb/CountCertificateProg/";
    public static final String GET_PROGRAMS_COUNT = "/API/Motadareb/CountAllProg/";
    public static final String GET_TEACHER_PROGRAMS_COUNT = "/API/Modareb/CountAllProg/";


    public static final String CHECK_PROGRAM_EXIST = "/API/Motadareb/CheckProgExists/";
    public static final String SUBMIT_REGISTER = "/API/Motadareb/Register/";
    public static final String GET_PROGRAM_DATA_BY_REF_ID = "/API/Motadareb/GetProgramdataByRefId/";

    public static final String GET_STUDENT_OF_PROGRAM = "/API/Modareb/GetAllMotadarebInProg/";

    public static final String GET_PROGRAM_STATUS = "/API/Modareb/ProgramStatus/";
}

