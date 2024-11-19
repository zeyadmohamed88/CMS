package conference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    // Save a list of objects to a file
    public static <T> void saveToFile(String fileName, List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
            System.out.println("Data saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Load a list of objects from a file
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName + ". Initializing new data.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
