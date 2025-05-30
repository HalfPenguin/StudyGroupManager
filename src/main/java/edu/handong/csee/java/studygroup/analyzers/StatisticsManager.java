package edu.handong.csee.java.studygroup.analyzers;

import edu.handong.csee.java.studygroup.datamodel.StudyGroup;

import java.util.HashMap;

/**
 * Utility class for generating and displaying statistics about study groups.
 * This class provides methods to analyze study group data and print statistical information.
 */
public final class StatisticsManager {

    /**
     * Prints basic statistics about the study groups.
     * This method outputs the group number, number of students, and number of courses for each group.
     *
     * @param groupInfo A HashMap containing study group information where keys are group IDs
     *                 and values are StudyGroup objects
     */
    public static void printGroupStatistics(HashMap<Integer, StudyGroup> groupInfo) {
        for (StudyGroup group : groupInfo.values()) {
            System.out.println("Group" + group.getGroupNo() +
                    ", # of students: " + group.getMemberIDs().size() +
                    ", # of courses for study: " + group.getCourseNames().size());
        }
    }
}