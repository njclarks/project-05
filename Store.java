import java.io.*;
import java.util.ArrayList;

/**
 * Stores a list of the user's products they wish to sell
 *
 * @author Nick Clarkson
 * @version 1
 */
public class Store {
    private User owner;
    private String storeName;

    // Constructor 
    public Store(User owner, String storeName) {
        this.owner = owner;
        this.storeName = storeName;
    }
    
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}   

