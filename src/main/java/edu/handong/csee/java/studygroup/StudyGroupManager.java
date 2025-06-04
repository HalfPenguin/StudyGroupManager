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
 * The main class for the Study Group Analyzer application.
 * This class orchestrates the entire process, including parsing command-line
 * arguments, reading data from a CSV file, processing the data, and
 * printing the results to the console or saving them to a file.
 */
public class StudyGroupManager {

    /**
     * The main entry point for the application.
     * It creates an instance of StudyGroupManager and calls the run method.
     *
     * @param args Command-line arguments provided by the user.
     */
    public static void main(String[] args) {
        StudyGroupManager myRunner = new StudyGroupManager();
        myRunner.run(args);
    }

    /**
     * Executes the main logic of the application.
     * It handles command-line options, reads and processes the study group
     * data, and generates statistics or filtered results based on user input.
     *
     * @param args Command-line arguments to be parsed.
     */
    public void run(String[] args) {

        OptionHandler myOptionHandler = new OptionHandler();
        Options options = myOptionHandler.createOption();

        if (myOptionHandler.parseOptions(options, args)) {

            // for -h option
            if (myOptionHandler.isPrintHelp()) {
                myOptionHandler.printHelp(options);
                return; // Exit after printing help
            }

            // read a file and get the String array list for records in the csv file.
            String filePath = myOptionHandler.getDataFilePath();
            System.out.println("Loading the study group data file, " + filePath + "...");

            String[] fieldNames = "Group,MemberID,MemberName,Friends,Subjects,Reports,Times".split(",");
            ArrayList<ArrayList<String>> records = FileUtils.readCSVFile(filePath, fieldNames);

            // get array list for Student instances from lines.
            ArrayList<Student> students = getStudents(records);

            // get hash map for group info
            HashMap<Integer, StudyGroup> groupInfo = DataPreprocessor.getGroupInfo(students);

            System.out.println("The data file is loaded...");
            System.out.println("The number of groups: " + groupInfo.size());
            System.out.println("The number of students: " + students.size());

            // for -s option
            if (myOptionHandler.isPrintStatistics()) {
                System.out.println();
                System.out.println("==== Statistics ====");
                StatisticsManager.printGroupStatistics(groupInfo);
            }

            // for -n option
            if (myOptionHandler.getCourseName() != null) {
                System.out.println();
                try {
                    String courseName = myOptionHandler.getCourseName();
                    ArrayList<StudyGroup> groupsForTheCourseName = DataPreprocessor.getGroupInfoByCourseName(groupInfo)
                            .get(courseName);

                    if (groupsForTheCourseName == null) {
                        throw new NoCourseNameFoundException(courseName);
                    }

                    ArrayList<String> header = new ArrayList<>();
                    header.add("Group");
                    header.add("MemberIDs");
                    header.add("MemberNames");
                    header.add("Reports");
                    header.add("Times");

                    // save results to a new CSV file
                    FileUtils.writeCSVFileByCourseName(filePath, courseName, header,
                            groupsForTheCourseName);

                    // print results to the console
                    System.out.println("Group,MemberIDs,MemberNames,Reports,Times");
                    for (StudyGroup group : groupsForTheCourseName) {
                        System.out.print(group.getGroupNo() + ",");
                        System.out.print("\"" + group.getMemberIDs().toString().replaceAll("^\\[|]$", "") + "\",");
                        System.out.print("\"" + group.getNames().toString().replaceAll("^\\[|]$", "") + "\",");
                        System.out.print(group.getNumOfReports() + ",");
                        System.out.println(group.getStudyMinutes());
                    }
                } catch (NoCourseNameFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Converts raw CSV data into a list of Student objects.
     * It iterates through each record from the CSV file and maps the relevant
     * columns to create a new Student instance. The "Friends" column is ignored.
     *
     * @param records A list where each inner list represents a row from the CSV file.
     * @return An ArrayList of Student objects.
     */
    public ArrayList<Student> getStudents(ArrayList<ArrayList<String>> records) {
        ArrayList<Student> students = new ArrayList<>();

        for (ArrayList<String> record : records) {
            // CSV columns: 0:Group, 1:MemberID, 2:MemberName, 3:Friends, 4:Subjects, 5:Reports, 6:Times
            Student student = new Student(record.get(0),   // Group
                    record.get(1),   // MemberID
                    record.get(2),   // MemberName
                    record.get(4),   // Subjects
                    record.get(5),   // Reports
                    record.get(6));  // Times
            students.add(student);
        }

        return students;
    }
}