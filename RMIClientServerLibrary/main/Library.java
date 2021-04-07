package main;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Library extends UnicastRemoteObject implements ILibrary 
{

    private Map<Book, Integer> availableBooks = new HashMap<>();
    private Map<Book, Queue<IClient>> bookQueue = new HashMap<>();

    public Library() throws RemoteException 
    {
        availableBooks.put(new Book("Lew Tołstoj", "Wojna i pokój", "3123545"), 3);
        availableBooks.put(new Book("Lew Tołstoj", "Anna Karenina", "7553323"), 5);
        availableBooks.put(new Book("Lew Tołstoj", "Zmartwychstanie", "1424633"), 1);
        availableBooks.put(new Book("Nikołaj Gogol", "Martwe Dusze", "1233145"), 1);
        availableBooks.put(new Book("Aleksander Puszkin", "Eugeniusz Oniegin", "7464222"), 1);
        availableBooks.put(new Book("Aleksander Puszkin", "Dama Pikowa", "7464222"), 1);
        availableBooks.put(new Book("Michaił Bułhakow", "Mistrz i Małgorzata", "7464222"), 2);
    }

    private Optional<Book> getBook(String bookTitle) 
    {
        return availableBooks.entrySet().stream().map(Entry::getKey).filter(book -> book.getTitle().equals(bookTitle)).findFirst();
    }

    @Override
    public String borrowBook(IClient client, String bookTitle) throws RemoteException 
    {
        Book book = getBook(bookTitle).orElse(null);
        Integer quantity = availableBooks.get(book);
        if (quantity == null)
        {
            return "No book found";
        }
        if (quantity < 1)
        {
            Queue<IClient> queue = bookQueue.getOrDefault(book, new LinkedList<>());
            queue.add(client);
            bookQueue.put(book, queue);
            return "Book not available, your are on a queue nr " + queue.size();
        }
        availableBooks.put(book, --quantity);
        return "Book is available. Borrowing request accepted. Borrowed book: " + book.toString();
    }

    @Override
    public String returnBook(IClient client, String bookTitle) throws RemoteException 
    {
        Book book = getBook(bookTitle).orElse(null); 
        Integer quantity = availableBooks.get(book);
        Queue<IClient> queue = bookQueue.get(book); 
        availableBooks.put(book,++quantity);
        try 
        {
            IClient nextPerson = bookQueue.get(book).poll();   
            nextPerson.confirmBorrowing(bookTitle);
        } 
        catch (Exception e) {}
        return "Book returned. Returned book " + book.toString();
    }

    @Override
    public Map<Book, Integer>  listBooks (String searchPhrase) throws RemoteException
    {
        if (searchPhrase == null || searchPhrase.isEmpty())
        {
            return availableBooks;
        }
        else
        {
            return availableBooks.entrySet().stream()
            .map(Entry::getKey)
            .filter(book -> book.toString().toLowerCase().contains(searchPhrase.toLowerCase()))
            .collect(Collectors.toMap(book -> book, book -> availableBooks.get(book))); 
        }
    }
}
