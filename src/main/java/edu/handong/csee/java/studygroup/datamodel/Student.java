package edu.handong.csee.java.studygroup.datamodel;

import java.util.ArrayList;

/**
 * Represents a student in a study group.
 * This class encapsulates all information related to an individual student
 * including group number, ID, name, friends, subjects, reports count, and study time.
 */
public class Student {
    private final int group;
    private final int memberID;
    private final String memberName;
    private final ArrayList<String> subjects = new ArrayList<>();
    private final int reports;
    private final int times;

    /**
     * Constructs a new Student object with the provided information.
     *
     * @param group      The group number the student belongs to
     * @param memberID   The unique identifier for the student
     * @param memberName The name of the student
     * @param subjects   A comma-separated string of subject/course names
     * @param reports    The number of reports completed
     * @param times      The time spent studying in minutes
     */
    public Student(String group, String memberID, String memberName, String subjects, String reports, String times) {
        this.group = Integer.parseInt(group.trim());
        this.memberID = Integer.parseInt(memberID.trim());
        this.memberName = memberName.trim();


        // Process subjects/courses
        if (subjects != null && !subjects.trim().isEmpty()) {
            String[] subjectsArr = subjects.split(",");
            for (String subject : subjectsArr) {
                if (subject != null && !subject.trim().isEmpty()) {
                    this.subjects.add(subject.trim());
                }
            }
        }

        this.reports = Integer.parseInt(reports.trim());
        this.times = Integer.parseInt(times.trim());
    }

    /**
     * Gets the group number of the student.
     *
     * @return The group number
     */
    public int getGroup() {
        return group;
    }

    /**
     * Gets the member ID of the student.
     *
     * @return The member ID
     */
    public int getMemberID() {
        return memberID;
    }

    /**
     * Gets the name of the student.
     *
     * @return The student's name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * Gets a copy of the student's subject/course list.
     *
     * @return A new ArrayList containing the student's subjects
     */
    public ArrayList<String> getSubjects() {
        return new ArrayList<>(subjects);
    }

    /**
     * Gets the number of reports completed by the student.
     *
     * @return The number of reports
     */
    public int getReports() {
        return reports;
    }

    /**
     * Gets the time spent studying in minutes.
     *
     * @return The study time in minutes
     */
    public int getTimes() {
        return times;
    }

}