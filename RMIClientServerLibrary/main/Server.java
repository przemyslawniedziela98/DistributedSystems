package main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    Registry reg;
    ILibrary library;
    
    public static void main(String[] args) 
    {
        try
        {
            new Server();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public Server () throws RemoteException 
    {
        try
        {
            reg = LocateRegistry.createRegistry(1099);
            library = new Library();
            reg.rebind("Library", library);
            System.out.println("Library registred");
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

    }

}