package edu.handong.csee.java.studygroup;

import edu.handong.csee.java.studygroup.analyzers.DataPreprocessor;
import edu.handong.csee.java.studygroup.analyzers.StatisticsManager;
import edu.handong.csee.java.studygroup.datamodel.Student;
import edu.handong.csee.java.studygroup.datamodel.StudyGroup;
import edu.handong.csee.java.studygroup.exceptions.NoCourseNameFoundException;
import edu.handong.csee.java.studygroup.fileio.FileUtils;
import edu.handong.csee.java.studygroup.cli.OptionHandler;
import org.apache.commons.cli.Options;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main class for the Study Group Manager application.
 * This class coordinates the different components of the application,
 * supporting both regular CSV files and ZIP archives containing CSV files.
 */
public class StudyGroupManager {

    /**
     * The entry point of the application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        StudyGroupManager myRunner = new StudyGroupManager();
        myRunner.run(args);
    }

    /**
     * Runs the application with the given command-line arguments.
     *
     * @param args Command-line arguments
     */
    public void run(String[] args) {
        OptionHandler myOptionHandler = new OptionHandler();
        Options options = myOptionHandler.createOption();

        if (myOptionHandler.parseOptions(options, args)) {
            // Check if help option was provided
            if (myOptionHandler.isPrintHelp()) {
                myOptionHandler.printHelp(options);
                return;
            }

            // Read the input file
            String filePath = myOptionHandler.getDataFilePath();
            System.out.println("Loading the data file: " + filePath + "...");


            String[] fieldNames = {"Group", "MemberID", "MemberName", "Friends", "Subjects", "Reports", "Times"};
            ArrayList<ArrayList<String>> records = FileUtils.readCSVFile(filePath, fieldNames);

            if (records.isEmpty()) {
                System.err.println("Error: No valid data found in the file.");
                return;
            }

            // Process student data
            ArrayList<Student> students = getStudents(records);

            // Get group information
            HashMap<Integer, StudyGroup> groupInfo = DataPreprocessor.getGroupInfo(students);

            System.out.println("The data file is loaded...");
            System.out.println("The number of groups: " + groupInfo.size());
            System.out.println("The number of students: " + students.size());

            // Handle statistics option
            if (myOptionHandler.isPrintStatistics()) {
                System.out.println();
                System.out.println("==== Statistics ====");
                StatisticsManager.printGroupStatistics(groupInfo);
            }

            // Handle course name option
            if (myOptionHandler.getCourseName() != null) {
                System.out.println();
                processCourseNameOption(myOptionHandler.getCourseName(), filePath, groupInfo);
            }
        }
    }

    /**
     * Processes the course name option and generates the corresponding output.
     * Displays members with their IDs and calculates average reports and time per member.
     *
     * @param courseName The course name to process
     * @param filePath The path to the input file
     * @param groupInfo The HashMap containing study group information
     */
    private void processCourseNameOption(String courseName, String filePath, HashMap<Integer, StudyGroup> groupInfo) {
        try {
            HashMap<String, ArrayList<StudyGroup>> groupInfoByCourseName = DataPreprocessor.getGroupInfoByCourseName(groupInfo);
            ArrayList<StudyGroup> groupsForTheCourseName = groupInfoByCourseName.get(courseName);

            if (groupsForTheCourseName == null) {
                throw new NoCourseNameFoundException(courseName);
            }

            // Prepare header for CSV file
            ArrayList<String> header = new ArrayList<>();
            header.add("Group");
            header.add("MemberIDs");
            header.add("MemberNames");
            header.add("Reports");
            header.add("Times");

            // Save to CSV file
            FileUtils.writeCSVFileByCourseName(filePath, courseName, header, groupsForTheCourseName);

            // Print to console
            System.out.println("Group,MemberIDs,MemberNames,Reports,Times");

            for (StudyGroup group : groupsForTheCourseName) {
                int groupNo = group.getGroupNo();
                ArrayList<Integer> idList = group.getMemberIDs();
                ArrayList<String> nameList = group.getMemberNames();

                // Calculate averages
                double avgReports = (double) group.getNumOfReports() / idList.size();
                double avgTimes = (double) group.getStudyMinutes() / idList.size();

                // Format the averages - use integer format if it's a whole number
                String formattedReports;
                String formattedTimes;

                if (avgReports == (int)avgReports) {
                    formattedReports = Integer.toString((int)avgReports);
                } else {
                    formattedReports = String.format("%.2f", avgReports);
                }

                if (avgTimes == (int)avgTimes) {
                    formattedTimes = Integer.toString((int)avgTimes);
                } else {
                    formattedTimes = String.format("%.2f", avgTimes);
                }

                System.out.print(groupNo + ",");
                System.out.print("\"" + String.join(", ", convertIntegersToStrings(idList)) + "\",");
                System.out.print("\"" + String.join(", ", nameList) + "\",");
                System.out.print(formattedReports + ",");
                System.out.println(formattedTimes);
            }
        } catch (NoCourseNameFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Converts a list of integers to an array of strings.
     *
     * @param integers The list of integers to convert
     * @return An array of strings
     */
    private String[] convertIntegersToStrings(ArrayList<Integer> integers) {
        String[] strings = new String[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            strings[i] = integers.get(i).toString();
        }
        return strings;
    }

    /**
     * Creates Student objects from the raw data records.
     *
     * @param records The list of raw data records
     * @return A list of Student objects
     */
    public ArrayList<Student> getStudents(ArrayList<ArrayList<String>> records) {
        ArrayList<Student> students = new ArrayList<>();

        for (ArrayList<String> record : records) {
            if (record.size() >= 7) {  // Ensure we have all required fields
                Student student = new Student(
                        record.get(0),      // Group
                        record.get(1),      // MemberID
                        record.get(2),      // MemberName
                        record.get(3),      // Friends
                        record.get(4),      // Subjects
                        record.get(5),      // Reports
                        record.get(6)       // Times
                );
                students.add(student);
            }
        }

        return students;
    }
}
