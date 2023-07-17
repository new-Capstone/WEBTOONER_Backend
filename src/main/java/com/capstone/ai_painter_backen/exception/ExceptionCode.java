package com.capstone.ai_painter_backen.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "자기 자신은 팔로우 할 수 없습니다"),
    FORBIDDEN_WORD_USED(BAD_REQUEST, "금지된 단어가 사용되었습니다 다시 입력해주세요"),
    ALREADY_FOLLOWED(BAD_REQUEST, "이미 팔로우되어 있습니다."),
    INVALID_REGISTER_TUTEE(BAD_REQUEST, "튜티 등록 시 자기 자신을 튜터로 등록할 수 없습니다."),
    TYPE_MISMATCH(BAD_REQUEST, "잘못된 타입을 입력했습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    EMAIL_AUTHENTICATION_NEED(UNAUTHORIZED,"이메일 인증을 다시 시도해 주세요"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    TUTOR_NOT_FOUND(NOT_FOUND, "해당 튜터를 찾을 수 없습니다"),
    TUTEE_NOT_FOUND(NOT_FOUND, "해당 튜티를 찾을 수 없습니다"),
    ROOM_NOT_FOUND(NOT_FOUND, "해당 룸을 찾을 수 없습니다"),
    MESSAGE_NOT_FOUND(NOT_FOUND, "해당 메시지를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "로그아웃 된 사용자입니다"),
    NOT_FOLLOW(NOT_FOUND, "팔로우 중이지 않습니다"),
    FILE_IS_NOT_EXIST(NOT_FOUND, "해당 파일이 존재하지 않습니다" ),
    NO_SUCH_ELEMENT(NOT_FOUND, "해당 요소는 존재하지 않습니다."),
    NO_SUCH_POST_ENTITY(NOT_FOUND, "해당 게시글은 존재하지 않습니다."),
    NO_FOLLOW_ENTITY_EXIST(NOT_FOUND, "팔로우 정보가 없습니다" ),
    NO_SUCH_CATEGORY(NOT_FOUND, "해당 카테고리는 존재하지 않습니다."),
    POST_NOT_FOUND(NOT_FOUND, "해당 포스트를 찾을 수 없습니다"),
    CHATROOM_NOT_FOUND(NOT_FOUND, "해당 채팅이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(NOT_FOUND,"해당 댓들을 찾을 수 없습니다"),
    EMAIL_TOKEN_NOT_FOUND(NOT_FOUND,"해당 이메일 토큰을 찾을 수 없습니다. 다시 입력해주세요"),


    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    DUPLICATE_DISPLAY_NAME(CONFLICT, "해당 닉네임은 이미 존재합니다."),
    DUPLICATE_EMAIL(CONFLICT, "해당 이메일은 이미 존재합니다."),
    DUPLICATE_TUTEE(CONFLICT, "해당 유저는 이미 튜티로 등록돼있습니다."),
    DORMANCY_DURATION_UNDER_2Y(CONFLICT, "휴면 기준인 2년에 맞지 않습니다."),
    DUPLICATE_CATEGORY_NAME(CONFLICT, "같은 이름의 카테고리가 이미 존재합니다"),
    PASSWORD_IS_WRONG(CONFLICT, "비밀번호가 잘못 입력되었습니다."),
    FILE_IS_NOT_EXIST_IN_BUCKET(CONFLICT, "해당 데이터가 존재하지 않습니다"),
    NONE_IMAGE_EXCEPTION(CONFLICT,"해당 이미지가 존재하지 않습니다"),
    REQUESTING_FILE_ALREADY_EXIST(CONFLICT,"해당 데이터가 이미 존재합니다"),
    ALREADY_EXSIT_MYPICK_USER(CONFLICT,"게시글의 좋아요는 한번만 가능합니다"),
    BEFORE_IMAGE_NOT_EXSIST(CONFLICT, "해당 변환전 이미지는 존재하지 않습니다"),

    ;





    private final HttpStatus httpStatus;
    private final String message;
}
