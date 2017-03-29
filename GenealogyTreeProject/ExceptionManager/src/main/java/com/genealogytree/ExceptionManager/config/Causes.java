package com.genealogytree.ExceptionManager.config;

public enum Causes {
    ANOTHER_CAUSE(0, "Another Error"),
    UNAUTHORIZED(401, "Required full authorization"),
    OPTIMISTIC_LOCK(111, "Row was updated or deleted by another transaction "),
    NO_VERSION_FIELD(110, "Persistence Entity must provided version"),
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
    PROJECT_NOT_FOUND(701, "Family not found"),
    PROJECT_ACCESS_VIOLATION(702, "Access Denied. This is not your project"),

    /*
    RELATION
     */
    TOO_MANY_NULL_FIELDS(810, "Too many null fields in relation"),
    INCORRECT_SEX(869, "Incorrect member sex in SimLeft or SimRight field"),
    INCORRECT_STATUS(811, "Incorrect relation status"),
    NO_VALID_MEMBERS(815, "No valid mermber in relation vas detected"),
    INTEGRATION_VILATION(888, "Genealogy Tree integration violation");



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
