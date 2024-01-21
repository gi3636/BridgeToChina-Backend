package com.btchina.core.api;

import lombok.Getter;

/**
 * 枚举了一些常用API操作码
 * Created by macro on 2019/4/19.
 */
@Getter
public enum ResultCode implements IErrorCode {


    SUCCESS(200, "validation.success"),
    FAILED(500, "validation.error"),
    VALIDATE_FAILED(404, "parameter.validation.failed"),
    ARGUMENT_NOT_VALID(400, "parameter.validation.failed"),
    UNAUTHORIZED(401, "not.logged.in.or.token.expired"),
    FORBIDDEN(403, "no.related.permissions"),
    TOKEN_INVALID(500, "token.validation.failed"),

    // User-related
    USER_NOT_FOUND(20001, "user.not.found"),
    PASSWORD_WRONG(20002, "incorrect.password"),
    USER_EXISTS(20003, "user.already.exists"),
    PASSWORD_NOT_SAME(20004, "passwords.not.match"),
    USER_NOT_LOGIN(20005, "user.not.logged.in"),
    GET_STS_TOKEN_ERROR(20100, "failed.to.get.stsToken"),
    UPLOAD_ERROR(20101, "file.upload.failed"),
    FILE_EMPTY(20102, "empty.file"),

    CREATED_INDEX_FAILED(20201, "index.creation.failed"),

    INDEX_EXISTS(20202, "index.already.exists"),
    QUERY_ERROR(20203, "data.query.error"),
    NOTE_NOT_EXIST(20204, "note.not.exists"),
    COMMENT_NOT_EXIST(20205, "comment.not.exists"),
    COMMENT_NOTE_NOT_MATCH(20206, "comment.note.not.match"),
    LOGIN_FAILED(20207, "login.failed"),
    QUESTION_NOT_EXIST(20208, "question.not.exists"),
    ANSWER_NOT_EXIST(20209, "answer.not.exists"),

    // Message-related
    MESSAGE_NOT_EXIST(20300, "message.not.exists"),
    NOTIFY_NOT_EXIST(20400, "notification.not.exists");


    private long code;
    private String key;
    private String message;

    ResultCode(long code, String key) {
        this.code = code;
        this.key = key;
    }




}
