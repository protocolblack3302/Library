package com.coderme.Library.Converters;
import com.coderme.Library.Domains.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;


@Slf4j
@Component
public class UrlToBookConverter implements GenericTransformer<String,Book> {

    @Override
    public Book transform (String sourceFile){
        File file = new File(sourceFile);
        PdfUtilities pdfUtilities = null;
        Book book = new Book();
        try {
            pdfUtilities = new PdfUtilities(file);
            book.setBookTitle(pdfUtilities.getTitle());
            book.setAuthor(pdfUtilities.getAuthor());
            book.setVersion(pdfUtilities.getVersion());
            book.setPages(pdfUtilities.getPages());
            book.setLink(sourceFile);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            if(pdfUtilities!=null){
                pdfUtilities.closeDocument();
            }
        }
        return book;
        }
}
