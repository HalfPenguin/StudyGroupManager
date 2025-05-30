//Information
The StudyGroupManager is a Java application designed to process and analyze data about students and their study groups from CSV files. 
It can take a single CSV file or a ZIP file containing multiple CSVs as input.

Using command-line arguments, the program tells:

Whether to display general statistics (like the number of groups and students).
To filter data for a specific course and show details like group members, average reports, and average study times for that course.
To save the course-specific information to a new CSV file.

The project is structured with classes to handle:

Reading and parsing command-line options (OptionHandler.java).
Representing data for individual students (Student.java) and study groups (StudyGroup.java).
Processing this data (e.g., organizing students into groups, filtering by course) (DataPreprocessor.java).
Calculating and displaying statistics (StatisticsManager.java).
Reading and writing CSV files (FileUtils.java).
A main class (StudyGroupManager.java) ties everything together.
It uses external libraries like Apache Commons CLI for command-line parsing and Apache Commons CSV for handling CSV files. 
It also includes a custom exception for cases where a specified course name isn't found (NoCourseNameFoundException.java).
