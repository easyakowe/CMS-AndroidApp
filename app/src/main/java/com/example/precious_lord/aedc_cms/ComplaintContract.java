package com.example.precious_lord.aedc_cms;

import android.provider.BaseColumns;

public class ComplaintContract {

    public static final class ComplaintEntry implements BaseColumns{

        public static final String COMPLAINT_TABLE = "complaint";
        public static final String COMP_ID = "comp_id";
        public static final String COMP_EMAIL = "comp_email";
        public static final String COMP_LOCATION = "comp_location";
        public static final String COMP_CATEGORY = "comp_category";
        public static final String COMP_SUB_CATEGORY = "comp_sub_category";
        public static final String COMP_TYPE = "comp_type";
        public static final String COMP_NATURE = "comp_nature";
        public static final String COMP_DETAIL = "comp_detail";
        public static final String COMP_REPLY = "comp_reply";
    }
}
