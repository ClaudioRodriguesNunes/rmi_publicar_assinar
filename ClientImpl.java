import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl implements Client {

    private String name;

    public ClientImpl() {}

    public void notify(String s) throws RemoteException {
        System.out.println("Tópico " + s + " publicado");
    }

    public String getName() throws RemoteException {
        return this.name;
    }

    public void setName(String name) throws RemoteException {
        this.name = name;
    }
    
    public static void main(String[] args) {
        try {
            Client c = new ClientImpl();
            c.setName(args[0].equals("-n") ? args[1] : null);
            Client stubC = (Client)UnicastRemoteObject.exportObject(c, 0);
            Registry registry = LocateRegistry.getRegistry();
            if(c.getName() != null)
                registry.bind(c.getName(), stubC);
            Server stubS = (Server) registry.lookup("RMIServer");
            System.err.println("Client ready");
            if (args[2].equals("-p")) {
                stubS.publicar(args[3]);
                System.err.println("Tópico " + args[3] + " públicado");
            } 
            if (args[2].equals("-a")) {
                //System.err.println("Entrou if assinar.");
                System.err.println(args[3]);
                stubS.assinar(c, args[3]);
                System.err.println("Assinatura feita com sucesso. Aguarde publicação.");
            }

        } catch (Exception e) {
            System.err.println("Exceção: " + e.toString());
        }
    }
}

