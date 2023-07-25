import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
/**
 * Responsible for general file IO and user UI
 *
 * @author Nick Clarkson
 * @version 1
 */
public class Marketplace
{
    private static ArrayList<User> users;
    private static ArrayList<Message> messages;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        FileIOHelper ioHelper = new FileIOHelper();
        users = ioHelper.parseUserFile();
        readMessageFile("messages.txt");
        if(users == null || messages == null) {
                return;
        }
        String input = "";
        boolean invalidInput = true;
        User current = null;
        while (true) {
            System.out.println("Welcome to the Marketplace!");
            while (true) {
                System.out.println("Do you have an account? Y/N or type exit to leave");
                input = scan.nextLine().toLowerCase();
                if(input.equals("y") || input.equals("n") || input.equals("exit")) {
                    break;
                } else {
                    System.out.println("Not a valid input, please enter a valid input.");
                }
            }
            invalidInput = true;
            if (input.equals("y")) {
                while (true) {
                    System.out.println("Please enter your username or enter exit to return to main menu");
                    input = scan.nextLine();
                    if (input.equals("exit")) {
                        break;
                    }
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getUsername().equals(input)) {
                            current = users.get(i);
                            break;
                        }
                    }
                    System.out.println("Please enter your password");
                    input = scan.nextLine();
                    if (current.getPassword().equals(input)) {
                        System.out.println("Welcome " + current.getName() + "!");
                        break;
                    }
                    if(current == null) {
                        System.out.println("Could not find user with that username and password. " + 
                            "Please try again or type exit to leave.");
                    }
                }
                if(current.isSeller()) {
                    runSeller(current, scan);
                } else {
                    runBuyer(current, scan);
                }
            } else if (input.equals("n")) {
                while (true) {
                    String name = "";
                    while (name.length() == 0) {
                        System.out.println("Please enter your name");
                        name = scan.nextLine();
                        if (name.length() == 0) {
                            System.out.println("Name cannot be empty.");
                        }
                    }
                    String email = "";
                    while (!email.contains("@") && !email.contains(".") && email.length() < 5) {
                        System.out.println("Please enter your email");
                        email = scan.nextLine();
                        if(!email.contains("@") && !email.contains(".")) {
                            System.out.println("That is not a valid email. Email must contain both @ and .");
                        } else if(email.length() < 5) {
                            System.out.println("Email cannot have any blank spaces. Please enter your full email.");
                        }
                    }
                    String username = "";
                    boolean uniqueUsername = false;
                    while (username.length() == 0 && !uniqueUsername) {
                        System.out.println("Please enter your username");
                        username = scan.nextLine();
                        for (int i = 0; i < users.size(); i++) {
                            if(users.get(i).getUsername().equals(username)) {
                                System.out.println("That username is taken. Please enter a different username.");
                            }
                        }
                        if(username.length() == 0) {
                            System.out.println("Username cannot be empty.");
                        }
                    }
                    String password = "";
                    while (password.length() == 0) {
                        System.out.println("Please enter your password");
                        password = scan.nextLine();
                        if(password.length() == 0) {
                            System.out.println("Password cannot be empty.");
                        }
                    }
                    String userType = "";
                    while (!userType.equals("buyer") && !userType.equals("seller")) {
                        System.out.println("Are you a buyer or a seller?");
                        userType = scan.nextLine().toLowerCase();
                        if(!userType.equals("buyer") && !userType.equals("seller")) {
                            System.out.println("That is not a valid input");
                        }
                    }
                    if (userType.equals("buyer")) {
                        current = new User(name, email, username, password, false);
                        users.add(current);
                        runBuyer(current, scan);
                    } else {
                        current = new User(name, email, username, password, true);
                        users.add(current);
                        runSeller(current, scan);
                    }
                    ioHelper.writeUserFile(users);
                    break;
                }
            } else if(input.equals("exit")) {
                System.out.println("Goodbye!");
                return;
            }
            ioHelper.writeMessageFile(messages);
        }
    }
    /*
     * Runs the seller section of the marketplace
     */
    public static void runSeller(User user, Scanner scan) {
        String oldUsername = user.getUsername();
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Settings");
            System.out.println("2. Messages");
            System.out.println("3. Sell an Item");
            System.out.println("4. Exit");
            int input;
            while (true) {
                try {
                    input = Integer.parseInt(scan.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid input");
                }
            }
            switch (input) {
                case 1 -> {
                    while (true) {
                        System.out.println("Settings:");
                        System.out.println("1. Change name");
                        System.out.println("2. Change username");
                        System.out.println("3. Change password");
                        System.out.println("4. Change email");
                        System.out.println("5. Change buyer/seller status");
                        System.out.println("6. Delete account");
                        System.out.println("7. Return to seller menu");
                        try {
                            input = Integer.parseInt(scan.nextLine());
                        } catch (Exception e) {
                            System.out.println("Please enter a valid input");
                        }
                        switch (input) {
                            case 1 -> {
                                String name = "";
                                while (name.length() == 0) {
                                    System.out.println("Please enter your new name");
                                    name = scan.nextLine();
                                    if (name.length() == 0) {
                                        System.out.println("Name cannot be empty.");
                                    }
                                }
                                user.setName(name);
                            }
                            case 2 -> {
                                String username = "";
                                boolean uniqueUsername = false;
                                while (username.length() == 0 && !uniqueUsername) {
                                    System.out.println("Please enter your new username");
                                    username = scan.nextLine();
                                    for (int i = 0; i < users.size(); i++) {
                                        if(users.get(i).getUsername().equals(username)) {
                                            System.out.println("That username is taken. Please enter a different username.");
                                        }
                                    }
                                    if(username.length() == 0) {
                                        System.out.println("Username cannot be empty.");
                                    }
                                }
                                user.setUsername(username);
                            }
                            case 3 -> {
                                String password = "";
                                while (password.length() == 0) {
                                    System.out.println("Please enter your new password");
                                    password = scan.nextLine();
                                    if(password.length() == 0) {
                                        System.out.println("Password cannot be empty.");
                                    }
                                }
                                user.setPassword(password);
                            }
                            case 4 -> {
                                String email = "";
                                while (!email.contains("@") && !email.contains(".") && email.length() < 5) {
                                    System.out.println("Please enter your new email");
                                    email = scan.nextLine();
                                    if(!email.contains("@") && !email.contains(".")) {
                                        System.out.println("That is not a valid email. Email must contain both @ and .");
                                    } else if(email.length() < 5) {
                                        System.out.println("Email cannot have any blank spaces. Please enter your full email.");
                                    }
                                }
                                user.setEmail(email);
                            }
                            case 5 -> {
                                String userType = "";
                                while (!userType.equals("buyer") && !userType.equals("seller")) {
                                    System.out.println("Are you a buyer or a seller?");
                                    userType = scan.nextLine().toLowerCase();
                                    if(!userType.equals("buyer") && !userType.equals("seller")) {
                                        System.out.println("That is not a valid input");
                                    }
                                }
                                if (userType.equals("buyer")) {
                                    user.setSeller(false);
                                } else {
                                    user.setSeller(true);
                                }
                                System.out.println("Returning you to main menu...");
                                return;
                            }
                            case 6 -> {
                                while (true) {
                                    System.out.println("Are you sure you want to delete your account? This cannot be undone. Y/N");
                                    String yn = scan.nextLine();
                                    if (yn.equals("Y")) {
                                        for (int i = 0; i < users.size(); i++) {
                                            if (users.get(i).getUsername().equals(user.getUsername())) {
                                                users.remove(i);
                                                System.out.println("Account successfully deleted.");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            case 7 -> {
                                break;
                            }
                            default -> {
                                System.out.println("Please enter a valid number");
                            }
                        }
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getUsername().equals(oldUsername)) {
                                users.set(i, user);
                            }
                        }
                    }
                }
                case 2 -> {
                    runMessages(user, scan);
                }
                case 3 -> {
                    
                }
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
        }
    }
    /*
     * Runs the buyer section of the marketplace
     */
    public static void runBuyer(User user, Scanner scan) {
        String oldUsername = user.getUsername();
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Settings");
            System.out.println("2. Messages");
            System.out.println("3. Browse the marketplace");
            System.out.println("4. Exit");
            int input;
            while (true) {
                try {
                    input = Integer.parseInt(scan.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid input");
                }
            }
            switch (input) {
                case 1 -> {
                    while (true) {
                        System.out.println("Settings:");
                        System.out.println("1. Change name");
                        System.out.println("2. Change username");
                        System.out.println("3. Change password");
                        System.out.println("4. Change email");
                        System.out.println("5. Change buyer/seller status");
                        System.out.println("6. Delete account");
                        System.out.println("7. Return to seller menu");
                        try {
                            input = Integer.parseInt(scan.nextLine());
                        } catch (Exception e) {
                            System.out.println("Please enter a valid input");
                        }
                        switch (input) {
                            case 1 -> {
                                String name = "";
                                while (name.length() == 0) {
                                    System.out.println("Please enter your new name");
                                    name = scan.nextLine();
                                    if (name.length() == 0) {
                                        System.out.println("Name cannot be empty.");
                                    }
                                }
                                user.setName(name);
                            }
                            case 2 -> {
                                String username = "";
                                boolean uniqueUsername = false;
                                while (username.length() == 0 && !uniqueUsername) {
                                    System.out.println("Please enter your new username");
                                    username = scan.nextLine();
                                    for (int i = 0; i < users.size(); i++) {
                                        if(users.get(i).getUsername().equals(username)) {
                                            System.out.println("That username is taken. Please enter a different username.");
                                        }
                                    }
                                    if(username.length() == 0) {
                                        System.out.println("Username cannot be empty.");
                                    }
                                }
                                user.setUsername(username);
                            }
                            case 3 -> {
                                String password = "";
                                while (password.length() == 0) {
                                    System.out.println("Please enter your new password");
                                    password = scan.nextLine();
                                    if(password.length() == 0) {
                                        System.out.println("Password cannot be empty.");
                                    }
                                }
                                user.setPassword(password);
                            }
                            case 4 -> {
                                String email = "";
                                while (!email.contains("@") && !email.contains(".") && email.length() < 5) {
                                    System.out.println("Please enter your new email");
                                    email = scan.nextLine();
                                    if(!email.contains("@") && !email.contains(".")) {
                                        System.out.println("That is not a valid email. Email must contain both @ and .");
                                    } else if(email.length() < 5) {
                                        System.out.println("Email cannot have any blank spaces. Please enter your full email.");
                                    }
                                }
                                user.setEmail(email);
                            }
                            case 5 -> {
                                String userType = "";
                                while (!userType.equals("buyer") && !userType.equals("seller")) {
                                    System.out.println("Are you a buyer or a seller?");
                                    userType = scan.nextLine().toLowerCase();
                                    if(!userType.equals("buyer") && !userType.equals("seller")) {
                                        System.out.println("That is not a valid input");
                                    }
                                }
                                if (userType.equals("buyer")) {
                                    user.setSeller(false);
                                } else {
                                    user.setSeller(true);
                                }
                                System.out.println("Returning you to main menu...");
                                return;
                            }
                            case 6 -> {
                                while (true) {
                                    System.out.println("Are you sure you want to delete your account? This cannot be undone. Y/N");
                                    String yn = scan.nextLine();
                                    if (yn.equals("Y")) {
                                        for (int i = 0; i < users.size(); i++) {
                                            if (users.get(i).getUsername().equals(user.getUsername())) {
                                                users.remove(i);
                                                System.out.println("Account successfully deleted.");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                            case 7 -> {
                                break;
                            }
                            default -> {
                                System.out.println("Please enter a valid number");
                            }
                        }
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getUsername().equals(oldUsername)) {
                                users.set(i, user);
                            }
                        }
                    }
                }
                case 2 -> {
                    runMessages(user, scan);
                }
                case 3 -> {
                    
                }
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
        }
    }
    public static void runMessages(User user, Scanner scan) {
        while (true) {
            int input;
            while (true) {
                System.out.println("Messages menu:");
                System.out.println("1. View inbox");
                System.out.println("2. View outbox");
                System.out.println("3. Send a message");
                System.out.println("4. Edit a message");
                System.out.println("5. Delete a message");
                System.out.println("6. Import a messages file");
                System.out.println("7. Exit");
                try {
                    input = Integer.parseInt(scan.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid input");
                }
            }
            switch (input) {
                case 1 -> {
                    user.printInbox();
                }
                case 2 -> {
                    user.printOutbox();
                }
                case 3 -> {
                    int recipientIndex = -1;
                    boolean invalidInput = true;
                    while (invalidInput) {
                        System.out.println("List of all users you can message: ");
                        for (int i = 0; i < users.size(); i++) {
                            if(user.isSeller() ^ users.get(i).isSeller()) {
                                System.out.println("- " + users.get(i).getUsername());
                            }
                        }
                        System.out.println("Enter the username of the user you would like to message or type exit to return to main menu");
                        String username = scan.nextLine();
                        if (username.equals("exit")) {
                            return;
                        }
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getUsername().equals(username)) {
                                if (user.isSeller() == users.get(i).isSeller()) {
                                    System.out.println("You cannot message a user of the same type");
                                    recipientIndex = -2;
                                    break;
                                }
                                recipientIndex = i;
                                invalidInput = false;
                            }
                        }
                        if (recipientIndex == -1) {
                            System.out.println("User not found, please enter a valid username");
                        }
                    }
                    String subject = "";
                    while (subject.length() == 0) {
                        System.out.println("Please enter the subject of the message");
                        subject = scan.nextLine();
                        if(subject.length() == 0) {
                            System.out.println("Subject cannot be empty");
                        }
                    }
                    String message = "";
                    while (message.length() == 0) {
                        System.out.println("Please enter the message you would like to send");
                        message = scan.nextLine();
                        if(message.length() == 0) {
                            System.out.println("Message cannot be empty");
                        }
                    }
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime currentTime = LocalDateTime.now();
                    String timestamp = dtf.format(currentTime);
                    Message newMessage = new Message(user, users.get(recipientIndex), subject, message, timestamp);
                    user.addToOutbox(newMessage);
                    users.get(recipientIndex).addToInbox(newMessage);
                    messages.add(newMessage);
                }
                case 4 -> {
                    int recipientIndex = -1;
                    boolean invalidInput = true;
                    if (user.getOutbox().size() == 0) {
                        System.out.println("There are no messages in your outbox to edit");
                    } else {
                        user.printOutbox();
                        ArrayList<Message> userOutbox = user.getOutbox();
                        while (invalidInput) {
                            System.out.println("Enter the recipient of the message you would like to edit");
                            String recipient = scan.nextLine();
                            for (int i = 0; i < userOutbox.size(); i++) {
                                if (userOutbox.get(i).getRecipient().equals(recipient)) {
                                    invalidInput = false;
                                    break;
                                }
                            }
                            for (int i = 0; i < users.size(); i++) {
                                if(users.get(i).getUsername().equals(recipient)) {
                                    recipientIndex = i;
                                    break;
                                }
                            }
                            if (invalidInput) {
                                System.out.println("You have not sent a message to a user with that name, please enter a valid username");
                            }
                        }
                    }
                }
                case 5 -> {
                    
                } 
                case 6 -> {
                    System.out.println("Enter the name of the message file you would like to import");
                    String fileName = scan.nextLine();
                    readMessageFile(fileName);
                }
                case 7 -> {
                    return;
                }
                default -> {
                    System.out.println("Please enter a valid number");
                }
            }
        }
    }
    
    /*
     * Reads and filters user messages from a message file. Requires a certain type of formatting to work:
     * sender~recipient~subject~message~timestamp
     */
    public static void readMessageFile(String fileName) {
        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            ArrayList<Message> messages = new ArrayList<Message>();
            while (line != null) {
                String[] newMessage = line.split("~");
                int senderIndex = -1;
                int recipientIndex = -1;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getName().equals(newMessage[0])) {
                        senderIndex = i;
                    } else if(users.get(i).getName().equals(newMessage[1])) {
                        recipientIndex = i;
                    }
                }
                Message message = new Message(users.get(senderIndex), users.get(recipientIndex), 
                    newMessage[2], newMessage[3], newMessage[4]);
                messages.add(message);
                users.get(senderIndex).addToOutbox(message);
                users.get(recipientIndex).addToInbox(message);
                line = br.readLine();
            }
            System.out.println("Message file successfully imported.");
        } catch (Exception e) {
            System.out.println("Error reading from message file. \n" + 
                "Please ensure file is in project directory and is formatted correctly.");
        }
    }
}
