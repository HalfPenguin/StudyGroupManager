package edu.handong.csee.java.studygroup.cli;

import org.apache.commons.cli.*;

/**
 * Handles command-line options for the study group analysis application.
 * This class defines, parses, and manages the command-line interface.
 */
public class OptionHandler {
    public void setPrintStatistics(boolean printStatistics) {
        this.printStatistics = printStatistics;
    }

    public void setPrintHelp(boolean printHelp) {
        this.printHelp = printHelp;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setDataFilePath(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    private String dataFilePath;
    private String courseName;
    private boolean printHelp;
    private boolean printStatistics;

    public boolean parseOptions(Options options, String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("h")) {
                printHelp = true;
            }

            if (cmd.hasOption("s")) {
                printStatistics = true;
            }

            if (cmd.hasOption("n")) {
                courseName = cmd.getOptionValue("n");
            }

            if (cmd.hasOption("f")) {
                dataFilePath = cmd.getOptionValue("f");
            }

        } catch (ParseException e) {
            printHelp(options);
            return false;
        }
        return true;
    }

    /**
     * Prints help information for the application.
     *
     * @param options The Options object containing defined command-line options
     */
    public void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        String header = "Study group analysis program";
        String footer = System.lineSeparator() + "Please report issues at the issue report system.";
        formatter.printHelp("SGAnalyzer -f <file-path> [-h] [-n <course-name>] [-s]", header, options, footer, false);
    }

    /**
     * Creates and defines the command-line options for the application.
     *
     * @return An Options object containing all defined command-line options
     */
    public Options createOption() {
        Options options = new Options();

        Option option = Option.builder("f")
                .longOpt("filepath")
                .desc("Set the data file path.")
                .hasArg()
                .argName("file-path")
                .required()
                .build();

        Option option2 = Option.builder("h")
                .longOpt("help")
                .desc("Print out the help page.")
                .build();

        Option option3 = Option.builder("n")
                .longOpt("cname")
                .desc("Set a course name so it will print out group information based on course names. In addition, it saves a csv file about the results.")
                .hasArg()
                .argName("course-name")
                .build();

        Option option4 = Option.builder("s")
                .longOpt("statistics")
                .desc("Print out the statistics of the study group data.")
                .build();

        options.addOption(option);
        options.addOption(option2);
        options.addOption(option3);
        options.addOption(option4);

        return options;
    }

    /**
     * Gets the course name.
     *
     * @return The course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Gets the data file path.
     *
     * @return The data file path
     */
    public String getDataFilePath() {
        return dataFilePath;
    }

    /**
     * Checks if help should be printed.
     *
     * @return true if help should be printed, false otherwise
     */
    public boolean isPrintHelp() {
        return printHelp;
    }

    /**
     * Checks if statistics should be printed.
     *
     * @return true if statistics should be printed, false otherwise
     */
    public boolean isPrintStatistics() {
        return printStatistics;
    }

}