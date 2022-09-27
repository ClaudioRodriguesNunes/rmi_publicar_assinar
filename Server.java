import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    public void publicar(String s) throws RemoteException;

    public void assinar(Client c, String s) throws RemoteException;
}
