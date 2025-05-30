package edu.handong.csee.java.studygroup.fileio;

import edu.handong.csee.java.studygroup.exceptions.ZipFileProcessingException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class for handling ZIP file operations.
 * This class provides methods to extract and process CSV files from ZIP archives
 * and also handle regular CSV files.
 */
public class ZipFileHandler {

    /**
     * Extracts and processes all CSV files from a ZIP archive or processes a regular CSV file.
     * It can handle both .zip files and .csv files directly.
     *
     * @param path The path to the file (either a regular CSV file or a ZIP file containing CSV files).
     * @param header The header row for the CSV files.
     * @return A map containing file names as keys and their data as values if it's a ZIP file,
     *         or a single list if it's a regular CSV file.
     * @throws ZipFileProcessingException If an error occurs during file processing.
     */
    public static Object readCSVFiles(String path, String[] header) throws ZipFileProcessingException {
        if (path.toLowerCase().endsWith(".zip")) {
            return readCSVFilesFromZip(path, header);  // Process ZIP file
        } else {
            return processCSVFile(path, header);  // Process regular CSV file
        }
    }

    /**
     * Processes a regular CSV file and returns the data as a list of lists of strings.
     *
     * @param filePath The path to the CSV file.
     * @param header   The header row of the CSV file.
     * @return A list of lists of strings representing the CSV data.
     * @throws ZipFileProcessingException If an error occurs during file processing.
     */
    private static ArrayList<ArrayList<String>> processCSVFile(String filePath, String[] header) throws ZipFileProcessingException {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            throw new ZipFileProcessingException("File not found: " + filePath);
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            // Use FileUtils method to parse the CSV content
            data = FileUtils.parseCSVContent(reader, header);
        } catch (IOException e) {
            throw new ZipFileProcessingException("Error reading CSV file: " + e.getMessage(), e);
        }

        return data;
    }

    /**
     * Extracts and processes all CSV files from a ZIP archive.
     *
     * @param zipFilePath The path to the ZIP file.
     * @param header      The header row for the CSV files.
     * @return A map containing file names as keys and their data as values.
     * @throws ZipFileProcessingException If an error occurs during ZIP file processing.
     */
    public static Map<String, ArrayList<ArrayList<String>>> readCSVFilesFromZip(String zipFilePath, String[] header) throws ZipFileProcessingException {
        Map<String, ArrayList<ArrayList<String>>> fileDataMap = new HashMap<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                String fileName = entry.getName();

                // Process only CSV files
                if (fileName.toLowerCase().endsWith(".csv") && !fileName.contains("__MACOSX")) {
                    ArrayList<ArrayList<String>> fileData = processCSVFromZipEntry(zipInputStream, header);
                    fileDataMap.put(fileName, fileData);
                }

                zipInputStream.closeEntry();
            }

            if (fileDataMap.isEmpty()) {
                throw new ZipFileProcessingException("No CSV files found in the ZIP archive: " + zipFilePath);
            }
        } catch (IOException e) {
            throw new ZipFileProcessingException("Error processing ZIP file: " + zipFilePath, e);
        }

        return fileDataMap;
    }

    /**
     * Reads a specific CSV file from the ZIP archive.
     *
     * @param zipFilePath      The path to the ZIP file.
     * @param targetCsvFileName The name of the target CSV file to read.
     * @param header           The header row to apply when parsing.
     * @return The data of the specified CSV file.
     * @throws ZipFileProcessingException If the file cannot be found or read.
     */
    public static ArrayList<ArrayList<String>> readSingleCSVFromZip(String zipFilePath, String targetCsvFileName, String[] header) throws ZipFileProcessingException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                String fileName = entry.getName();

                // Match the file name (ignore paths and __MACOSX entries)
                if (fileName.toLowerCase().endsWith(targetCsvFileName.toLowerCase()) && !fileName.contains("__MACOSX")) {
                    return processCSVFromZipEntry(zipInputStream, header);
                }

                zipInputStream.closeEntry();
            }

            throw new ZipFileProcessingException("CSV file '" + targetCsvFileName + "' not found in the ZIP archive: " + zipFilePath);
        } catch (IOException e) {
            throw new ZipFileProcessingException("Error reading CSV file from ZIP: " + targetCsvFileName, e);
        }
    }

    /**
     * Processes a single CSV file from a ZIP entry.
     *
     * @param zipInputStream The ZipInputStream positioned at the current entry.
     * @param header The header row for the CSV file.
     * @return A list of lists containing the CSV data.
     * @throws IOException If an I/O error occurs.
     */
    private static ArrayList<ArrayList<String>> processCSVFromZipEntry(ZipInputStream zipInputStream, String[] header) throws IOException {
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        // Read the ZIP entry into a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len;
        while ((len = zipInputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }

        // Convert byte array to input stream for CSV processing
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
             InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

            // Use the existing FileUtils method to parse the CSV content
            data = FileUtils.parseCSVContent(reader, header);
        }

        return data;
    }

    /**
     * Lists the names of all CSV files in a ZIP archive.
     *
     * @param zipFilePath The path to the ZIP file.
     * @return A list containing the names of all CSV files in the archive.
     * @throws ZipFileProcessingException If an error occurs during ZIP file processing.
     */
    public static ArrayList<String> listCSVFilesInZip(String zipFilePath) throws ZipFileProcessingException {
        ArrayList<String> csvFiles = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                String fileName = entry.getName();

                // Add only CSV files (excluding __MACOSX system files)
                if (fileName.toLowerCase().endsWith(".csv") && !fileName.contains("__MACOSX")) {
                    csvFiles.add(fileName);
                }

                zipInputStream.closeEntry();
            }

            if (csvFiles.isEmpty()) {
                throw new ZipFileProcessingException("No CSV files found in the ZIP archive: " + zipFilePath);
            }
        } catch (IOException e) {
            throw new ZipFileProcessingException("Error listing CSV files in ZIP: " + zipFilePath, e);
        }

        return csvFiles;
    }
}
