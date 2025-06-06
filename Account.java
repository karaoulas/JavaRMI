package messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

    String username;
    String authToken;
    List<Message> messageBox;

    ;

    public Account(String name, String tok) {
        this.messageBox = new ArrayList<>();
        username = name;
        authToken = tok;
    }

    public void addMessage(Message m) {
        messageBox.add(m);
    }

    public void destroyMessage(int index) {
        messageBox.remove(index);
    }

    @Override
    public String toString() {
        return "Account{" + "username=" + username + ", authToken=" + authToken + ", messageBox=" + messageBox + '}';
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setMessageBox(List<Message> messageBox) {
        this.messageBox = messageBox;
    }

}
