package com.example.aki.javaq.Domain.Helper;

/**
 * Created by MinaFujisawa on 2017/07/21.
 */

public class FirebaseNodes {

    public static final class User {
        public static final String USER_CHILD = "users";
        public static final String USER_NAME = "mUserName";
        public static final String USER_PIC_URI = "mUserPicUri";
        public static final String USER_ID = "mUid";
    }

    public static final class PostMain {
        public static final String POSTS_CHILD = "posts";
        public static final String POST_BODY = "mPostBody";
        public static final String USER_ID = "mUserId";
        public static final String POST_TIME = "mPostTime";
    }
}
