package com.epam.rd.november2017.vlasenko.entity;

public class Book {
    private String name;
    private String author;
    private String publisher;
    private String publicationDate;
    private int count;

    public Book(String name, String author, String publisher, String publicationDate, int count) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.count = count;
    }
    
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public int getCount() {
        return count;
    }
}
