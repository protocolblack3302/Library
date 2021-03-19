package com.coderme.Library.Domains;

import com.coderme.Library.Repository.BookRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Component
public class WholeBookCatalogue {
    private  List<String> wholeNames;
    private List<String> matchingNames;

    private final BookRepository bookRepository;

    public boolean hasBook(String searchQuery) {
        wholeNames=bookRepository.getAllTitles();
        matchingNames= wholeNames.parallelStream().filter(names->names.toLowerCase().contains(searchQuery.toLowerCase())).collect(Collectors.toList());
        return !matchingNames.isEmpty();
    }

    public List<Book> getBooks() {
        ArrayList<Book> booksFound = new ArrayList<>();
        for(String matchingName : matchingNames){
            booksFound.add(bookRepository.getBookByBookTitle(matchingName));
        }
        return booksFound;
    }

}
