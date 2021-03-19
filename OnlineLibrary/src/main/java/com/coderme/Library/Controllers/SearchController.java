package com.coderme.Library.Controllers;

import com.coderme.Library.Domains.SearchObject;
import com.coderme.Library.Domains.WholeBookCatalogue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;


@Controller
@Slf4j
@Data
public class SearchController {
    private final WholeBookCatalogue bookCatalogue;

    @GetMapping("/search")
    public String getSearchBox(Model model){
        model.addAttribute("searchObject",new SearchObject());
        return "search";
    }


    @PostMapping("/search")
    public String getBook(@Valid SearchObject searchObject,Errors errors ,Model model){
        if(errors.hasErrors()){
            log.info(" search field has Errors !");
            return "search";
        }

        if(bookCatalogue.hasBook(searchObject.getSearchString())){
            log.info("Keyword Found !");
            model.addAttribute("books",bookCatalogue.getBooks());
            return "bookList";
        }else {
            log.error("Book not found !");
            model.addAttribute("status","! oops nothing found");
            return "search";
        }

    }

}
