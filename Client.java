package messaging;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class Client {

    public static void main(String[] args) {
//        RMISecurityManager security = new RMISecurityManager();
//        System.setSecurityManager(security);
        try {
            String name = "//" + args[0] + "/MessagingServer";
            Messaging lu = (Messaging) Naming.lookup(name);
            String FN_ID = args[1];
            String authtoken = "-1";
            //int res = lu.addnums(11, 55);
            if (FN_ID.equals("1")) {
                System.out.println("Executing CreateAccount method.");
                authtoken = lu.createAccount(args[2]);
                System.out.println(authtoken);
            } else if (FN_ID.equals("2")) {
                String token = args[2];
                System.out.println("Executing ShowAccounts method.");
                String accs = lu.showAccounts(token);
                System.out.println(accs);
            } else if (FN_ID.equals("3")) {
                System.out.println("Executing SendMessage method.");
                String token = args[2];
                String recipient = args[3];
                String body = args[4];
                String messageProgress = lu.sendMessage(token, recipient, body);
                System.out.println(messageProgress);
            } else if (FN_ID.equals("4")) {
                System.out.println("Executing ShowInbox method.");
                String token = args[2];
                String messages = lu.showInbox(token);
                System.out.println(messages);
            } else if (FN_ID.equals("5")) {
                System.out.println("Executing ReadMessage method.");
                String token = args[2];
                String id = args[3];
                String message = lu.readMessage(token, id);
                System.out.println(message);
            } else if (FN_ID.equals("6")) {
                System.out.println("Executing DeleteMessage method.");
                String token = args[2];
                String id = args[3];
                String del = lu.deleteMessage(token, id);
                System.out.println(del);
                System.out.println("Message box after deleting the message:");
                String messages = lu.showInbox(token);
                System.out.println(messages);
            } else {
                System.out.println("You gave an invalid FN_ID.");
            }
        } catch (Exception e) {
            System.out.println("Server error:" + e);
            System.exit(1);
        }
    }
}
