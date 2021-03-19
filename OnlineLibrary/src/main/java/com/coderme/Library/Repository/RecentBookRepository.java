package com.coderme.Library.Repository;

import com.coderme.Library.Domains.Book;

import java.util.ArrayDeque;
import java.util.Queue;

public interface RecentBookRepository {
    Queue<Book> recentBooks= new ArrayDeque<>();
    void feedQueue(Book book);
    void logRecentBooks();

}
