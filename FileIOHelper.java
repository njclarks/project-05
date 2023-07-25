import java.util.*;
import java.io.*;
/**
 * Write a description of class FileIOHandler here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FileIOHelper
{
    private final static String userFile = "users.txt";
    private final static String messageFile = "messages.txt";
    public static ArrayList<User> parseUserFile() {
        try (FileReader fr = new FileReader(userFile)) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<User> users = new ArrayList<User>();
            while (line != null) {
                String[] newUser = line.split(",");
                boolean isSeller = Boolean.parseBoolean(newUser[4]);
                users.add(new User(newUser[0], newUser[1], newUser[2], newUser[3], isSeller));
                line = br.readLine();
            }
            System.out.println("users.txt file successfully imported.");
            return users;
        } catch (Exception e) {
            System.out.println("Error reading from users.txt file. " + 
                "Please ensure file exists within project directory \n" + 
                "and is formatted correctly.");
            return null;
        }
    }
    
    public static void writeUserFile(ArrayList<User> users) {
        try (PrintWriter pw = new PrintWriter(userFile)) {
            for (int i = 0; i < users.size(); i++) {
                pw.println(users.get(i).toString());
            }
        } catch (Exception e) {
            System.out.println("Error writing to users.txt file. Please ensure \n" + 
                "file exists within project directory.");
        }
    }
    
    public static void writeMessageFile(ArrayList<Message> messages) {
        try (PrintWriter pw = new PrintWriter(messageFile)) {
            for (int i = 0; i < messages.size(); i++) {
                pw.println(messages.get(i).toString());
            }
        } catch (Exception e) {
            System.out.println("Error writing to messages.txt file. Please ensure \n" + 
                "file exists within project directory.");
        }
    }
}
