package com.example.aki.javaq.Domain.Helper;

/**
 * Created by MinaFujisawa on 2017/07/21.
 */

public class FirebaseNodes {

    public static final class User {
        public static final String USER_CHILD = "users";
        public static final String USER_NAME = "userName";
        public static final String USER_PIC_URI = "userPicUri";
        public static final String USER_ID = "uid";
    }

    public static final class UserPicture {
        public static final String USER_PIC_CHILD = "user_pictures";
    }

    public static final class PostMain {
        public static final String POSTS_CHILD = "posts";
        public static final String POST_BODY = "postBody";
        public static final String USER_ID = "userId";
        public static final String POST_TIME = "postTime";
        public static final String COMMENTS_NUM = "commentsNum";
    }

    public static final class PostComment {
        public static final String POSTS_COM_CHILD = "postcomments";
        public static final String POST_COM_BODY = "comBody";
        public static final String USER_ID = "userId";
        public static final String POST_COM_TIME = "comTime";
        public static final String POST_ID = "postId";
        public static final String LIKE = "comLike";
        public static final String UNLIKE = "comUnLike";
        public static final String IS_LIKEED = "comUnLiked";
        public static final String IS_UNLIKEED = "comUnLike";
    }
}
