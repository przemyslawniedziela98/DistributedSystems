package main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import main.IClient;

public interface ILibrary extends Remote
{
    String borrowBook(IClient client, String bookTitle) throws RemoteException;
    String returnBook(IClient client, String bookTitle) throws RemoteException;
    Map<Book, Integer>  listBooks(String searchPhrase) throws RemoteException; 
}
