package edu.handong.csee.java.studygroup.analyzers;

import edu.handong.csee.java.studygroup.datamodel.Student;
import edu.handong.csee.java.studygroup.datamodel.StudyGroup;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Utility class for processing student data and organizing it into study groups.
 * This class provides methods to analyze and transform raw student data.
 */
public final class DataPreprocessor {

    /**
     * Processes a list of students and organizes them into study groups.
     * This method aggregates student information by group number.
     *
     * @param students The list of students to process
     * @return A HashMap where keys are group IDs and values are StudyGroup objects
     */
    public static HashMap<Integer, StudyGroup> getGroupInfo(ArrayList<Student> students) {
        HashMap<Integer, StudyGroup> groupInfo = new HashMap<>();

        for (Student student : students) {
            int groupNumber = student.getGroup();

            // Initialize group if not present
            if (!groupInfo.containsKey(groupNumber)) {
                groupInfo.put(groupNumber, new StudyGroup(groupNumber));
            }

            StudyGroup group = groupInfo.get(groupNumber);

            // Add member ID and name as a pair to ensure they stay synchronized
            int memberID = student.getMemberID();
            String memberName = student.getMemberName();

            // Use the new addMember method to ensure proper pairing
            group.addMember(memberID, memberName);

            group.setNumOfReports(student.getReports());
            group.setStudyMinutes(student.getTimes());

            // Add all course names (subjects)
            for (String course : student.getSubjects()) {
                if (course != null && !course.isEmpty()) {
                    group.addCourseName(course);
                }
            }
        }

        return groupInfo;
    }

    /**
     * Organizes study group information by course name.
     * This method creates a mapping from course names to lists of study groups.
     *
     * @param mapGroupInfo The HashMap containing study group information by group ID
     * @return A HashMap where keys are course names and values are lists of StudyGroup objects
     */
    public static HashMap<String, ArrayList<StudyGroup>> getGroupInfoByCourseName(HashMap<Integer, StudyGroup> mapGroupInfo) {
        HashMap<String, ArrayList<StudyGroup>> groupInfoByCourseName = new HashMap<>();

        for (StudyGroup group : mapGroupInfo.values()) {
            for (String course : group.getCourseNames()) {
                if (!groupInfoByCourseName.containsKey(course)) {
                    groupInfoByCourseName.put(course, new ArrayList<>());
                }

                ArrayList<StudyGroup> groupsForCourse = groupInfoByCourseName.get(course);
                if (!groupsForCourse.contains(group)) {
                    groupsForCourse.add(group);
                }
            }
        }

        return groupInfoByCourseName;
    }
}