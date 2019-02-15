package com.example.precious_lord.aedc_cms;

import android.provider.BaseColumns;

public class StaffContract {

    public static final class StaffEntry implements BaseColumns{

        public static final String STAFF_TABLE = "staff";
        public static final String ID = "id";
        public static final String STAFF_ID = "staff_id";
        public static final String STAFF_FULL_NAME = "staff_fullname";
        public static final String STAFF_EMAIL = "staff_email";
        public static final String STAFF_PHONE = "staff_phone";
        public static final String STAFF_PASSWORD = "staff_password";
    }
}
