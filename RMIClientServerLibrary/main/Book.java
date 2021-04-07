package main;


import java.io.*;


public class Book implements Serializable
{
    private String autor;
    private String title;
    private String ISBN;

    public Book(String autor, String title, String ISBN) 
    {
        this.autor = autor;
        this.title = title;
        this.ISBN = ISBN;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAutor()
    {
        return autor;
    }
    
    public String getISBN()
    {
        return ISBN;
    }

    @Override
    public String toString() {
        return autor + ", " + title + ", " + ISBN;
    }

    
}