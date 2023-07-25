import java.util.*;

/**
 * Stores a user's info
 *
 * @author Nick Clarkson
 * @version 1
 */
public class User {
    private String name;
    private String email;
    private String username;
    private String password;
    private boolean isSeller;
    private ArrayList<Message> inbox;
    private ArrayList<Message> outbox;
    public User(String name, String email, String username, String password, boolean isSeller) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
        inbox = new ArrayList<Message>();
        outbox = new ArrayList<Message>();
    }

    // Getters and Setters

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
    public boolean setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
            System.out.println("Email changed successfully");
            return true;
        } else {
            System.out.println("Invalid email");
        }
        return false;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public boolean setPassword(String password) {
        if (password.length() >= 8) {
            this.password = password;
            System.out.println("Password changed successfully");
            return true;
        } else {
            System.out.println("Password must be at least 8 characters long");
        }
        return false;
    }
    public boolean isSeller() {
        return isSeller;
    }
    public void setSeller(boolean isSeller) {
        this.isSeller = isSeller;
    }
    public ArrayList<Message> getInbox() {
        return inbox;
    }
    public ArrayList<Message> getOutbox() {
        return inbox;
    }
    public void addToInbox(Message message) {
        inbox.add(message);
    }
    public void addToOutbox(Message message) {
        outbox.add(message);
    }
    public void removeFromInbox(Message message) {
        for (int i = 0; i < inbox.size(); i++) {
            if(inbox.get(i).equals(message)) {
                inbox.remove(i);
                System.out.println("Message successfully removed");
                return;
            }
        }
        System.out.println("Error: Message not found in user's inbox");
    }
    public void removeFromOutbox(Message message) {
        for (int i = 0; i < outbox.size(); i++) {
            if(outbox.get(i).equals(message)) {
                outbox.remove(i);
                System.out.println("Message successfully removed");
                return;
            }
        }
        System.out.println("Error: Message not found in user's outbox");
    }
    
    public void printInbox() {
        if(inbox.size() == 0) {
            System.out.println("Inbox empty");
            return;
        }
        for (int i = 0; i < inbox.size(); i++) {
            System.out.println(inbox.get(i).toString());
        }
    }
    public void printOutbox() {
        if(outbox.size() == 0) {
            System.out.println("Outbox empty");
            return;
        }
        for (int i = 0; i < outbox.size(); i++) {
            System.out.println(outbox.get(i).toString());
        }
    }
    
    public String toString() {
        return name + "," + email + "," + username + "," + password + "," + isSeller;
    }
    
    public boolean equals(User user) {
        if (this.username.equals(user.getUsername())) {
            return true;
        }
        return false;
    }
    
}


 
