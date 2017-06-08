package com.linked_sys.hasatraining.core;


import com.linked_sys.hasatraining.models.Program;

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
    //USER
    public String ACode = "";
}
