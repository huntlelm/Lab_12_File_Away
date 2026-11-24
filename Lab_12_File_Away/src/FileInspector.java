import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;

public class FileInspector {
    public static void main(String[] args) {

        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;
        String fileName = "";

        try {
            File workingDirectory = new File(System.getProperty("user.dir") + "/src");
            chooser.setCurrentDirectory(workingDirectory);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();
                fileName = selectedFile.getName();

                InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                System.out.println("Processing file: " + fileName + "\n");

                // Read file line by line
                while (reader.ready()) {
                    rec = reader.readLine();
                    lineCount++;

                    // Echo the line to the screen
                    System.out.println(rec);

                    // Add characters in this line to total
                    charCount += rec.length();

                    // Count words using split
                    if (!rec.trim().isEmpty()) {
                        String[] words = rec.trim().split("\\s+");
                        wordCount += words.length;
                    }
                }
                reader.close();

                // Print the Summary Report
                System.out.println("\nSUMMARY REPORT:");
                System.out.println("File Name: " + fileName);
                System.out.println("Number of Lines: " + lineCount);
                System.out.println("Number of Words: " + wordCount);
                System.out.println("Number of Characters: " + charCount);

            } else {
                System.out.println("No file selected. Program execution cancelled.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}