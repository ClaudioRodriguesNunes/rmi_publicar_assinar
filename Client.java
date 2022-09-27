import java.rmi.*;

public interface Client extends Remote {
    
    public void notify(String s) throws RemoteException;
    public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;

}
