package main;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Scanner;
import javax.lang.model.util.ElementScanner14;

import main.IClient;


public class Client extends UnicastRemoteObject implements IClient
{
    transient ILibrary library;
    private transient Scanner userInput = new Scanner(System.in);
    String clientName;
    String bookName;
    String listSearchBorrowOrReturn;

    public static void main(String[] args) throws RemoteException
    {
        if (args.length < 1) new Client("localhost") ;
        else new Client(args[0]);   
    }

    public Client(String hostname) throws RemoteException
    {
        Registry reg;
        try 
        {
            reg = LocateRegistry.getRegistry(hostname);
            library = (ILibrary) reg.lookup("Library");
            getUserRequest();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    private void getUserRequest() throws RemoteException
    {
        while (true) 
        {
            System.out.println("Do you want to list availelable books, search book, borrow or return book (L/S/B/R)?");
            listSearchBorrowOrReturn = userInput.nextLine();
            if ("L".equalsIgnoreCase(listSearchBorrowOrReturn)) displayMap(library.listBooks(null));
            else if ("S".equalsIgnoreCase(listSearchBorrowOrReturn)) 
            {
                System.out.println("Enter search phrase: ");
                String searchPhrase = userInput.nextLine();
                displayMap(library.listBooks(searchPhrase));
            }
            else if ("B".equalsIgnoreCase(listSearchBorrowOrReturn))
            {
                System.out.println("Enter book name:");
                bookName = userInput.nextLine();
                String borrowResponse = library.borrowBook(this, bookName);
                System.out.println(borrowResponse);
            }
            else if ("R".equalsIgnoreCase(listSearchBorrowOrReturn))
            {
                System.out.println("Enter book name:");
                bookName = userInput.nextLine();
                String returnResponse = library.returnBook(this, bookName);
                System.out.println(returnResponse);
            }
            else System.out.println("Invalid input data");
        }
    }
    private void displayMap(Map<Book, Integer> bookMap)
    {
        for (Book value : bookMap.keySet())
        {
            String availelable;
            if (bookMap.get(value) == null || bookMap.get(value) < 1) availelable = "not availelable";
            else availelable = "availelable";
            System.out.println(value.toString() +", "+ availelable);
        }
    }

    public void confirmBorrowing(String returnedBookName) throws RemoteException 
    {
        System.out.println("Book you requested is avalilelable.");
        String borrowResponse = library.borrowBook(this, returnedBookName);
        System.out.println(borrowResponse);
    }
}
