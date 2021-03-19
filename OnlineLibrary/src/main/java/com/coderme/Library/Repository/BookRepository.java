package com.coderme.Library.Repository;

import com.coderme.Library.Domains.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    @Query(value = "select book_title from books" , nativeQuery = true)
    List<String> getAllTitles();
    Book getBookByBookTitle(String title);

}
