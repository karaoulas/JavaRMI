package messaging;

import java.io.Serializable;

public class Message implements Serializable {

    boolean isRead;
    String sender;
    String receiver;
    String body;

    public Message(String sender, String receiver, String body) {
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        isRead = false;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Message{" + "isRead=" + isRead + ", sender=" + sender + ", receiver=" + receiver + ", body=" + body + '}';
    }

}
