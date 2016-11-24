package com.genealogytree.ExceptionManager.config;

public enum Causes {
    ANOTHER_CAUSE(0, "Another Error"),
    UNAUTHORIZED(401, "Required full authorization"),
    /*
    * USERS
     */
    USER_ALREADY_EXIST(600, "Sorry, but user with this login exist already"),
    NOT_FOUND_USER(601, "User not found"),
    USER_INISTANCE_WITHOUT_ID(602, "User Id must be provided"),
    LOGIN_PASSWORD_INCORRECT(603, "Login or Password Incorrect"),

    /*
    * MEMBER
    */


    /*
    *  FAMILY
     */
    PROJECT_NOT_FOUND(601, "Family not found");






    private int code;
    private String cause;

    private Causes(int number, String cause) {
        this.cause = cause;
        this.code = number;
    }

    @Override
    public String toString() {
        return this.cause;
    }

    public int getCode() {
        return this.code;
    }
}
