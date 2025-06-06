package messaging;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface Messaging extends Remote {

    public String createAccount(String uname) throws RemoteException;

    public String showAccounts(String auth) throws RemoteException;

    public String sendMessage(String auth, String recipient, String body) throws RemoteException;

    public String showInbox(String auth) throws RemoteException;

    public String readMessage(String auth, String msgid) throws RemoteException;

    public String deleteMessage(String auth, String msgid) throws RemoteException;

}
