package main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote 
{
    public void confirmBorrowing(String bookName) throws RemoteException;
}
