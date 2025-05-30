package edu.handong.csee.java.studygroup.datamodel;

import java.util.ArrayList;

/**
 * Represents a student in a study group.
 * This class encapsulates all information related to an individual student
 * including group number, ID, name, friends, subjects, reports count, and study time.
 */
public class Student {
    private int group;
    private int memberID;
    private String memberName;
    private ArrayList<String> friends;
    private ArrayList<String> subjects;
    private int reports;
    private int times;

    /**
     * Constructs a new Student object with the provided information.
     *
     * @param group The group number the student belongs to
     * @param memberID The unique identifier for the student
     * @param memberName The name of the student
     * @param friends A comma-separated string of friend names
     * @param subjects A comma-separated string of subject/course names
     * @param reports The number of reports completed
     * @param times The time spent studying in minutes
     */
    public Student(String group, String memberID, String memberName, String friends, String subjects, String reports, String times) {
        this.group = Integer.parseInt(group.trim());
        this.memberID = Integer.parseInt(memberID.trim());
        this.memberName = memberName.trim();

        // Initialize the ArrayList before using it
        this.friends = new ArrayList<>();
        this.subjects = new ArrayList<>();

        // Process friends
        if (friends != null && !friends.isEmpty()) {
            String[] friendsArr = friends.split(",");
            for (String friend : friendsArr) {
                if (friend != null && !friend.trim().isEmpty()) {
                    this.friends.add(friend.trim());
                }
            }
        }

        // Process subjects/courses
        if (subjects != null && !subjects.isEmpty()) {
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
     * Sets the group number of the student.
     *
     * @param group The new group number
     */
    public void setGroup(int group) {
        this.group = group;
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
     * Sets the member ID of the student.
     *
     * @param memberID The new member ID
     */
    public void setMemberID(int memberID) {
        this.memberID = memberID;
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
     * Sets the name of the student.
     *
     * @param memberName The new student name
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * Gets a copy of the student's friends list.
     *
     * @return A new ArrayList containing the student's friends
     */
    public ArrayList<String> getFriends() {
        return new ArrayList<>(friends);
    }

    /**
     * Sets the student's friends list.
     *
     * @param friends The new list of friends
     */
    public void setFriends(ArrayList<String> friends) {
        this.friends = new ArrayList<>(friends);
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
     * Sets the student's subject/course list.
     *
     * @param subjects The new list of subjects
     */
    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = new ArrayList<>(subjects);
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
     * Sets the number of reports completed by the student.
     *
     * @param reports The new number of reports
     */
    public void setReports(int reports) {
        this.reports = reports;
    }

    /**
     * Gets the time spent studying in minutes.
     *
     * @return The study time in minutes
     */
    public int getTimes() {
        return times;
    }

    /**
     * Sets the time spent studying in minutes.
     *
     * @param times The new study time in minutes
     */
    public void setTimes(int times) {
        this.times = times;
    }
}