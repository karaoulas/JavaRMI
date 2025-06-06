package messaging;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.*;

public class MessagingServer extends UnicastRemoteObject implements Messaging {

    String unameRegex = "^(?=[a-zA-Z0-9_])$";//ekfrasi gia ton elegxo tou username 
    Pattern pattern = Pattern.compile(unameRegex);
    List<Account> accounts = new ArrayList<>();//h lista me tous logariasmous

    AtomicInteger token = new AtomicInteger(0);//typos akairaiou pou voithaei sthn automath paragwgh aplwn akeraiwn gia authtokens

    public MessagingServer() throws RemoteException {
        super();
    }

    @Override
    public String createAccount(String uname) throws RemoteException {
//        Matcher m = pattern.matcher(uname);//elegxos gia to pattern tou username
//        if (m.matches()) {
//            System.out.println("Valid username");
//        } else {
//            return "Invalid username";
//        }
        Iterator<Account> iterator = accounts.iterator();

        while (iterator.hasNext()) {
            Account acc = iterator.next();
            if (acc.getUsername().equals(uname)) {
                return "Sorry, the user already exists";
            }
        }
        int tok = token.incrementAndGet();//arxikopoihsh kainouriou authtoken
        String authtoken = String.valueOf(tok);//metatroph apo integer se string gia megaluterh euxereia
        Account a = new Account(uname, authtoken);//dhmiourgia logariasmou mesw tou constructor
        System.out.println(a);
        accounts.add(a);//prosthiki tou antijeimenoy sth lista accounts
        return "Authentication token:" + String.valueOf(a.getAuthToken());//epistrofh tou authentication token ston client

    }

    @Override
    public String showAccounts(String auth) throws RemoteException {
        Iterator<Account> iterator = accounts.iterator();//dhlwsh iteraror gia thn epivevaiwsh tou authentication token
        while (iterator.hasNext()) {
            Account acc = iterator.next();
            if (acc.getAuthToken().equals(auth)) {//ektelesh leitourgias mono otan epivevaiwthei to authtoken
                String result = "";
                for (int i = 0; i < accounts.size(); i++) {
                    result = result + "\n" + ((i + 1) + ". " + accounts.get(i).getUsername());//kataxwrhsh twn usernames se string gia na epistrafei ston client
                }
                return result;
            }
        }

        return "Invalid authentication token!";
    }

    @Override
    public String sendMessage(String auth, String recipient, String body) throws RemoteException {
        Iterator<Account> iterator = accounts.iterator();//dhlwsh iteraror gia thn epivevaiwsh tou authentication token
        while (iterator.hasNext()) {
            Account author = iterator.next();
            if (author.getAuthToken().equals(auth)) {//ektelesh leitourgias mono otan epivevaiwthei to authtoken
                String sender = author.getUsername();
                Iterator<Account> iterator2 = accounts.iterator();//dhlwsh deuterou iteraror gia thn epivevaiwsh tou authentication token
                while (iterator2.hasNext()) {
                    Account receiver = iterator2.next();
                    if (receiver.getUsername().equals(recipient)) {
                        receiver.addMessage(new Message(sender, recipient, body));//dhmiourgia mhnumatos kai prosthiki sto messagebox TOU RECEIVER
                        System.out.println(receiver);
                    }
                }
                return "The message has been sent successfully!";
            }
        }

        return "Invalid authentication token!";
    }

    @Override
    public String showInbox(String auth) throws RemoteException {
        Iterator<Account> iterator = accounts.iterator();//dhlwsh iteraror gia thn epivevaiwsh tou authentication token
        while (iterator.hasNext()) {
            Account acc = iterator.next();
            if (acc.getAuthToken().equals(auth)) {//ektelesh leitourgias mono otan epivevaiwthei to authtoken
                List<Message> messages = acc.getMessageBox();//h lista me tous logariasmous
                System.out.println(messages);
                String result = "";
                for (int i = 0; i < messages.size(); i++) {
                    Message m = messages.get(i);//to antikeimeno pou anhkei sthn lista me ta mhnymata
                    result = result + "\n" + ((i) + ". from: " + messages.get(i).getSender());//kataxwrhsh twn mhnymatwn se string gia na epistrafei ston client
                }
                return result;
            }
        }

        return "Invalid authentication token!";
    }

    @Override
    public String readMessage(String auth, String msgid) throws RemoteException {
        Iterator<Account> iterator = accounts.iterator();//dhlwsh iteraror gia thn epivevaiwsh tou authentication token
        while (iterator.hasNext()) {
            Account acc = iterator.next();
            if (acc.getAuthToken().equals(auth)) {//ektelesh leitourgias mono otan epivevaiwthei to authtoken
                List<Message> messages = acc.getMessageBox();//h lista me tous logariasmous
                System.out.println(messages);
                String result;
                int id = Integer.parseInt(msgid);
                if (id < messages.size()) {//elegxos gia swsto id
                    Message m = messages.get(id);//arxikopoihsh toy antikeimenoy mhnymatos analoga me th thesh pou epilexthike
                    result = m.getBody();
                } else {
                    result = "Message ID does not exist";
                }
                return result;
            }
        }

        return "Invalid authentication token!";
    }

    @Override
    public String deleteMessage(String auth, String msgid) throws RemoteException {
        Iterator<Account> iterator = accounts.iterator();//dhlwsh iteraror gia thn epivevaiwsh tou authentication token
        while (iterator.hasNext()) {
            Account acc = iterator.next();
            if (acc.getAuthToken().equals(auth)) {//ektelesh leitourgias mono otan epivevaiwthei to authtoken
                List<Message> messages = acc.getMessageBox();//h lista me tous logariasmous
                System.out.println(messages);
                String result;
                int id = Integer.parseInt(msgid);
                if (id < messages.size()) {//elegxos gia swsto id
                    acc.destroyMessage(id);//klhsh ths methodou pou anhkei sthn klash Account
                    result = "OK";
                } else {
                    result = "Message does not exist";
                }
                return result;
            }
        }

        return "Invalid authentication token!";
    }

    public static void main(String[] args) {
//        RMISecurityManager security = new RMISecurityManager();
//        System.setSecurityManager(security);

        try {
            MessagingServer server = new MessagingServer();
            int portNumber = Integer.parseInt(args[0]);
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(portNumber);//port number for rmi
            Naming.rebind("//localhost/MessagingServer", server);
            System.out.println("Server up...");
        } catch (Exception e) {
            System.out.println("Server error:" + e);
            System.exit(1);
        }
    }

}
