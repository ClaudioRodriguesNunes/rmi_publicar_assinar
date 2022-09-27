import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerImpl implements Server {

    HashMap<String, ArrayList<Client>> clientes = new HashMap<String, ArrayList<Client>>();
    
    ServerImpl() {
        clientes.put("TopicoTeste", new ArrayList<Client>());
    }

    public void publicar(String s) {
        System.err.println(s + " publicado");
        if(!clientes.containsKey(s)) {
            clientes.put(s, new ArrayList<Client>());
        }
        ArrayList<Client> assinantes = clientes.get(s);
        System.err.println("Assinantes dessa publicacao: ");
        for (Client client : assinantes) {
            try {
                System.err.println(client.getName());
                client.notify(s);
            } catch (Exception e) {
                System.err.println("Exceção: " + e.toString());
            }
        }
    }

    public void assinar(Client c, String s) {
        try{
            //Registry registry = LocateRegistry.getRegistry();
            // stubc = (Client) registry.lookup(c.getName())
            if(!clientes.containsKey(s)) {
                clientes.put(s, new ArrayList<Client>());
            }
            ArrayList<Client> listaAssinantes = clientes.get(s);
            listaAssinantes.add(c);
            System.err.println("Tópico " + s + " assinado.");
        } catch (Exception e) {
            System.err.println("Exceção: " + e.toString());
        }
    }

    public static void main(String[] args) {
        try {
            ServerImpl server = new ServerImpl();
            Server stub = (Server) UnicastRemoteObject.exportObject(server, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("RMIServer", stub);
            System.out.println("Teste");

        } catch (Exception e) {
            System.err.println("Server Exception: " + e.toString());
        }
    }
}
