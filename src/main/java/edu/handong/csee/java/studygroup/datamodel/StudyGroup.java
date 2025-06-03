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
    private ArrayList<String> courseNames;
    private ArrayList<String> memberNames;
    private int numOfReports;
    private int studyMinutes;
    // Track which names correspond to which IDs
    private HashMap<Integer, String> memberMap;

    /**
     * Constructs a new StudyGroup with all properties specified.
     *
     * @param groupNumber The group's identifier number
     * @param memberIDs List of member IDs in the group
     * @param courseNames List of course names the group studies
     * @param memberNames List of member names in the group
     * @param numOfReports Number of reports completed by the group
     * @param studyMinutes Total study time in minutes
     */
    public StudyGroup(int groupNumber, ArrayList<Integer> memberIDs, ArrayList<String> courseNames,
                      ArrayList<String> memberNames, int numOfReports, int studyMinutes) {
        this.groupNumber = groupNumber;
        this.memberIDs = new ArrayList<>(memberIDs);
        this.courseNames = new ArrayList<>(courseNames);
        this.memberNames = new ArrayList<>(memberNames);
        this.numOfReports = numOfReports;
        this.studyMinutes = studyMinutes;
        this.memberMap = new HashMap<>();

        // Initialize member map if possible
        int minSize = Math.min(memberIDs.size(), memberNames.size());
        for (int i = 0; i < minSize; i++) {
            this.memberMap.put(memberIDs.get(i), memberNames.get(i));
        }
    }

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
     * Sets the group number.
     *
     * @param groupNumber The new group number
     */
    public void setGroupNo(int groupNumber) {
        this.groupNumber = groupNumber;
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
     * Sets the list of member IDs.
     *
     * @param memberIDs The new list of member IDs
     */
    public void setMemberIDs(ArrayList<Integer> memberIDs) {
        this.memberIDs = new ArrayList<>(memberIDs);
    }

    /**
     * Gets the list of member names.
     *
     * @return The list of member names
     */
    public ArrayList<String> getMemberNames() {
        // Ensure the list is in the same order as member IDs
        ArrayList<String> orderedNames = new ArrayList<>();
        ArrayList<String> orderedNames1 = orderedNames;
        for (Integer id : memberIDs) {
            String name = memberMap.get(id);
            orderedNames1.add(Objects.requireNonNullElse(name, "Unknown"));
        }
        return orderedNames;
    }

    /**
     * Sets the list of member names.
     *
     * @param memberNames The new list of member names
     */
    public void setMemberNames(ArrayList<String> memberNames) {
        this.memberNames = new ArrayList<>(memberNames);
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
     * Sets the list of course names.
     *
     * @param courseNames The new list of course names
     */
    public void setCourseNames(ArrayList<String> courseNames) {
        this.courseNames = new ArrayList<>(courseNames);
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
     * Adds a member ID to the group and ensures the corresponding name is tracked.
     *
     * @param memberID The member ID to add
     */
    public void addMemberID(int memberID) {
        if (!this.memberIDs.contains(memberID)) {
            this.memberIDs.add(memberID);
        }
    }

    /**
     * Adds a member name to the group, ensuring it's linked to the corresponding member ID.
     *
     * @param memberName The member name to add
     */
    public void addMemberName(String memberName) {
        // Add to the names list if it's not already there
        if (!this.memberNames.contains(memberName)) {
            this.memberNames.add(memberName);
        }

        // Make sure to update the memberMap if we can determine which ID this name belongs to
        if (memberIDs.size() == memberNames.size()) {
            int index = memberNames.indexOf(memberName);
            if (index >= 0 && index < memberIDs.size()) {
                memberMap.put(memberIDs.get(index), memberName);
            }
        }
    }

    /**
     * Adds a member with both ID and name to ensure they're properly linked.
     *
     * @param memberID The member ID
     * @param memberName The corresponding member name
     */
    public void addMember(int memberID, String memberName) {
        if (!this.memberIDs.contains(memberID)) {
            this.memberIDs.add(memberID);
        }

        // Update the map
        memberMap.put(memberID, memberName);

        // Only add the name if it's not already in the list
        if (!this.memberNames.contains(memberName)) {
            this.memberNames.add(memberName);
        }
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