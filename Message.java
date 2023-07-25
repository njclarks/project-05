
/**
 * Stores information about a message
 *
 * @author Nick Clarkson
 * @version 1
 */
public class Message
{
    private User sender;
    private User recipient;
    private String subject;
    private String message;
    private String timestamp;
    public Message(User sender, User recipient, String subject, String message, String timestamp)
    {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    
    public User getRecipient() {
        return recipient;
    }
    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
    
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String toString() {
        return sender.getUsername() + "," + recipient.getUsername() + "," + subject + "," + timestamp;
    }
    
    public boolean equals(Message message) {
        if(this.sender.equals(message.getSender()) && this.recipient.equals(message.getRecipient()) && this.subject.equals(message.getSubject())) {
            return true;
        }
        return false;
    }
}
