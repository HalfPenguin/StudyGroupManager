package edu.handong.csee.java.studygroup.fileio;

import edu.handong.csee.java.studygroup.datamodel.StudyGroup;
import org.apache.commons.csv.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


/**
 * Utility class for file input/output operations.
 * This class provides methods to read and write CSV files for study group data.
 * Supports both regular CSV files and CSV files within ZIP archives.
 */
public class FileUtils {

    /**
     * Reads a CSV file or ZIP file containing CSV files and returns the data as a list of lists of strings.
     *
     * @param path   The path to the CSV or ZIP file.
     * @param header The header row of the CSV file.
     * @return A list of lists of strings representing the data, or an empty list if an error occurs.
     */
    public static ArrayList<ArrayList<String>> readCSVFile(String path, String[] header) {
            return processCSVFile(path, header);  // Process regular CSV file
    }

    /**
     * Processes a regular CSV file and extracts its data.
     *
     * @param filePath The path to the CSV file.
     * @param header   The header row of the CSV file.
     * @return A list of lists of strings representing the data, or an empty list if an error occurs.
     */
    private static ArrayList<ArrayList<String>> processCSVFile(String filePath, String[] header) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.err.println("Error: File not found - " + filePath);
            return data;
        }

        // Read and parse the CSV file
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            data = parseCSVContent(reader, header);
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return data;
    }
    

    /**
     * Parses CSV content from a Reader and returns the data as a list of lists of strings.
     *
     * @param reader The Reader containing CSV content.
     * @param header The header row of the CSV file.
     * @return A list of lists of strings representing the data, or an empty list if an error occurs.
     */
    public static ArrayList<ArrayList<String>> parseCSVContent(Reader reader, String[] header) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        try (CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .builder()
                .setHeader(header)
                .setSkipHeaderRecord(true)
                .build())) {

            for (CSVRecord record : csvParser) {
                ArrayList<String> row = new ArrayList<>();
                for (String column : header) {
                    row.add(record.get(column));
                }
                data.add(row);
            }

        } catch (IOException e) {
            System.err.println("Error parsing CSV content: " + e.getMessage());
        }

        return data;
    }

    /**
     * Writes study group data to a CSV file, filtered by course name.
     * Calculates average reports and study minutes per member.
     *
     * @param originalFileName The original name of the input file.
     * @param courseName       The course name to filter by.
     * @param header           The header row for the output CSV file.
     * @param groups           The list of study groups to write.
     */
    public static void writeCSVFileByCourseName(String originalFileName, String courseName, ArrayList<String> header, ArrayList<StudyGroup> groups) {
        String oDirectory = "output";
        File directory = new File(oDirectory);
        if (!directory.exists()) {
            boolean mkdirs = directory.mkdirs();
        }

        String baseFileName;
        if (originalFileName.toLowerCase().endsWith(".zip")) {
            baseFileName = new File(originalFileName).getName();
        } else {
            baseFileName = new File(originalFileName).getName().replace(".csv", "");
        }

        String outputFileName = oDirectory + "/" + baseFileName + "-" + courseName + ".csv";

        try (Writer writer = new FileWriter(outputFileName, StandardCharsets.UTF_8);
             CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .builder()
                     .setHeader(header.toArray(new String[0]))
                     .build())) {

            for (StudyGroup group : groups) {
                int groupNo = group.getGroupNo();
                ArrayList<Integer> idList = group.getMemberIDs();
                ArrayList<String> nameList = group.getMemberNames();

                ArrayList<String> idStrings = toStringList(idList);

                double avgReports = (double) group.getNumOfReports() / idList.size();
                double avgTimes = (double) group.getStudyMinutes() / idList.size();

                String formattedReports = (avgReports == (int) avgReports) ? Integer.toString((int) avgReports) : String.format("%.2f", avgReports);
                String formattedTimes = (avgTimes == (int) avgTimes) ? Integer.toString((int) avgTimes) : String.format("%.2f", avgTimes);

                printer.printRecord(
                        groupNo,
                        String.join(", ", idStrings),
                        String.join(", ", nameList),
                        formattedReports,
                        formattedTimes
                );
            }

            System.out.println("The output file, " + outputFileName + ", is saved!!");

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    /**
     * Converts a list of integers to a list of strings.
     *
     * @param memberIDs The list of integers to convert.
     * @return A list of strings representing the integers.
     */
    private static ArrayList<String> toStringList(ArrayList<Integer> memberIDs) {
        ArrayList<String> list = new ArrayList<>();
        for (Integer memberID : memberIDs) {
            list.add(memberID.toString());
        }
        return list;
    }
}
