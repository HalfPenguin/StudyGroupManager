package edu.handong.csee.java.studygroup.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a study group containing multiple students.
 * This class aggregates information about group members, courses, and study metrics.
 */
public class StudyGroup {
    private int groupNumber;
    private ArrayList<Integer> memberIDs;

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    private ArrayList<String> courseNames;

    public HashMap<Integer, String> getMemberMap() {
        return memberMap;
    }

    public void setMemberNames(ArrayList<String> memberNames) {
        this.memberNames = memberNames;
    }

    public void setCourseNames(ArrayList<String> courseNames) {
        this.courseNames = courseNames;
    }

    public void setMemberIDs(ArrayList<Integer> memberIDs) {
        this.memberIDs = memberIDs;
    }

    private ArrayList<String> memberNames;
    private int numOfReports;
    private int studyMinutes;
    // Track which names correspond to which IDs
    private final HashMap<Integer, String> memberMap;

    /**
     * Constructs a new, empty StudyGroup with the specified group number.
     *
     * @param groupNumber The group's identifier number
     */
    public StudyGroup(int groupNumber) {
        this.groupNumber = groupNumber;
        this.memberIDs = new ArrayList<>();
        this.courseNames = new ArrayList<>();
        this.memberNames = new ArrayList<>();
        this.numOfReports = 0;
        this.studyMinutes = 0;
        this.memberMap = new HashMap<>();
    }

    /**
     * Gets the group number.
     *
     * @return The group number
     */
    public int getGroupNo() {
        return groupNumber;
    }

    /**
     * Gets the list of member IDs.
     *
     * @return The list of member IDs
     */
    public ArrayList<Integer> getMemberIDs() {
        return new ArrayList<>(memberIDs);
    }

    /**
     * Gets the list of member names.
     *
     * @return The list of member names
     */
    public ArrayList<String> getMemberNames() {
        // Ensure the list is in the same order as member IDs
        ArrayList<String> orderedNames = new ArrayList<>();
        for (Integer id : memberIDs) {
            String name = memberMap.get(id);
            orderedNames.add(Objects.requireNonNullElse(name, "Unknown"));
        }
        return orderedNames;
    }

    /**
     * Gets the list of course names.
     *
     * @return The list of course names
     */
    public ArrayList<String> getCourseNames() {
        return new ArrayList<>(courseNames);
    }

    /**
     * Gets the number of reports completed by the group.
     *
     * @return The number of reports
     */
    public int getNumOfReports() {
        return numOfReports;
    }

    /**
     * Sets the number of reports completed by the group.
     * Adds the provided value to the existing report count.
     *
     * @param numOfReports The additional number of reports to add
     */
    public void setNumOfReports(int numOfReports) {
        this.numOfReports += numOfReports;
    }

    /**
     * Gets the total study time in minutes.
     *
     * @return The study time in minutes
     */
    public int getStudyMinutes() {
        return studyMinutes;
    }

    /**
     * Sets the total study time in minutes.
     * Adds the provided value to the existing study minutes.
     *
     * @param studyMinutes The additional study minutes to add
     */
    public void setStudyMinutes(int studyMinutes) {
        this.studyMinutes += studyMinutes;
    }

    /**
     * Adds a member with both ID and name to ensure they're properly linked.
     *
     * @param memberID The member ID
     * @param memberName The corresponding member name
     */
    public void addMember(int memberID, String memberName) {
        if (!this.memberIDs.contains(memberID) || !this.memberNames.contains(memberName)) {
            this.memberIDs.add(memberID);
        }

        // Update the map
        memberMap.put(memberID, memberName);
    }

    /**
     * Adds a course name to the group if it doesn't already exist.
     *
     * @param courseName The course name to add
     */
    public void addCourseName(String courseName) {
        if (!this.courseNames.contains(courseName)) {
            this.courseNames.add(courseName);
        }
    }

    /**
     * @deprecated Use getMemberNames() instead.
     * @return The list of member names
     */
    @Deprecated
    public ArrayList<String> getNames() {
        return getMemberNames();
    }

    /**
     * @deprecated Use getNumOfReports() instead.
     * @return The number of reports
     */
    @Deprecated
    public int getReport() {
        return getNumOfReports();
    }

    /**
     * @deprecated Use getStudyMinutes() instead.
     * @return The study time in minutes
     */
    @Deprecated
    public int getTimes() {
        return getStudyMinutes();
    }
}