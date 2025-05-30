package edu.handong.csee.java.studygroup.exceptions;

import java.io.Serial;

/**
 * Custom exception class to handle the scenario where a specified course name is
 * not found in the study group data.
 */
public class NoCourseNameFoundException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the NoCourseNameFoundException.
     *
     * @param courseName The course name that was not found.
     */
    public NoCourseNameFoundException(String courseName) {
        super("Exception-01: No course name (" + courseName + ") found!");
    }
}