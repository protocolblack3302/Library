package com.coderme.Library.Domains;

import com.coderme.Library.Repository.BookRepository;
import com.coderme.Library.Repository.RecentBookRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Data
@Slf4j
@ConfigurationProperties("bookfetch")

@Service
public class RecentBookUpdater implements Serializable, RecentBookRepository {

   private final BookRepository bookRepository;
   public static int pagedBooks = 2;

   @Override
   public void feedQueue(Book book) {
      if (recentBooks.size() == pagedBooks) {
         recentBooks.poll();
         recentBooks.add(book);
      }
      recentBooks.add(book);
      bookRepository.save(book);
      log.info("recently added new book : " + recentBooks);

   }

   @Override
   public void logRecentBooks() {
      log.info(recentBooks.toString());
   }
}