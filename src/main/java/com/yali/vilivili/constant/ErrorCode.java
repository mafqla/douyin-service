package com.yali.vilivili.constant;

/**
 * @Description 错误码
 * @Author yalis
 * @Date 2023-07-24 16:00
 */
public enum ErrorCode {


    // 用户相关错误状态码
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_ALREADY_EXIST(1002, "用户已存在"),
    LOGIN_FAILED(1003, "登录失败"),
    ACCESS_DENIED(1004, "拒绝访问"),
    ACCOUNT_DISABLED(1005, "账号被禁用"),


    // 视频和评论相关错误状态码
    VIDEO_NOT_FOUND(2001, "视频不存在"),
    VIDEO_DELETED(2002, "视频已被删除"),
    VIDEO_UPLOAD_FAILED(2003, "视频上传失败"),
    VIDEO_LIKE_FAILED(2004, "帖子点赞失败"),
    VIDEO_UNLIKE_FAILED(2005, "帖子取消点赞失败"),
    VIDEO_FAVORITE_FAILED(2006, "帖子收藏失败"),
    VIDEO_UNFAVORITE_FAILED(2007, "帖子取消收藏失败"),

    // 关注和粉丝相关错误状态码
    FOLLOW_SUCCESS(3001, "关注成功"),
    UNFOLLOW_SUCCESS(3002, "取消关注成功"),
    FOLLOW_LIST_GET_SUCCESS(3003, "粉丝列表获取成功"),
    ALREADY_FOLLOWING(3004, "用户已关注"),
    NOT_FOLLOWING(3005, "用户未关注"),

    // 评论相关错误状态码
    COMMENT_NOT_FOUND(4001, "评论不存在"),
    COMMENT_DELETED(4002, "评论已被删除"),
    COMMENT_LIKE_FAILED(4003, "评论点赞失败"),
    COMMENT_UNLIKE_FAILED(4004, "评论取消点赞失败");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ;


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 根据错误状态码获取对应的错误信息
     *
     * @param code 错误状态码
     * @return 对应的错误信息
     */
    public static String getErrorMessage(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.code == code) {
                return errorCode.message;
            }
        }
        return "Unknown Error";
    }
}
