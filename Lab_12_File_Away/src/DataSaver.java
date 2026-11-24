import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import static java.nio.file.StandardOpenOption.CREATE;

public class DataSaver {
    public static void main(String[] args) {
        Scanner sca = new Scanner(System.in);
        ArrayList<String> recs = new ArrayList<>();
        boolean doneAdd = false;

        // Loop to collect multiple records
        do {
            String firstName = SafeInput.getNonZeroLenString(sca, "Enter first name");
            String lastName = SafeInput.getNonZeroLenString(sca, "Enter last name");
            String idNumber = SafeInput.getRegExString(sca, "Enter ID number (6 digits)", "^\\d{6}$");
            String email = SafeInput.getNonZeroLenString(sca, "Enter email address");
            int yob = SafeInput.getRangedInt(sca, "Enter year of birth (4 digit integer)", 1000, 2026);

            // CSV format
            String csvRecord = firstName + ", " + lastName + ", " + idNumber + ", " + email + ", " + yob;

            // Add the record to the ArrayList
            recs.add(csvRecord);

            // Ask if they want to add another
            doneAdd = SafeInput.getYNConfirm(sca, "Would you like to add another record?");
            System.out.println();

        } while (doneAdd);

        // Prompt user for the file name
        String fileName = SafeInput.getNonZeroLenString(sca, "Enter the file name (without extension)");

        // Set path to save in the src directory with .csv extension
        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath() + "/src/" + fileName + ".csv");

        try {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            // Loop through the ArrayList and write each record to the file
            for (String rec : recs) {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("\nData file written successfully to: " + file.toAbsolutePath());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}