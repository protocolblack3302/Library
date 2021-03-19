package com.coderme.Library.Services;

import com.coderme.Library.Domains.Book;
import com.coderme.Library.Repository.RecentBookRepository;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.messaging.MessageHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;

@Configuration
@Data
public class Beans {

    private final RecentBookRepository repository;



    //password Encoder bean for security
    @Bean
    PasswordEncoder getPassword(){
        return new BCryptPasswordEncoder();
    }



    //spring integration file adapter to scan directory
    @Bean
     FileReadingMessageSource fileInboundAdapter(){
        File file = new File(DirectoryProp.directory);
        FileReadingMessageSource fileReadingMessageSource = new FileReadingMessageSource();
        fileReadingMessageSource.setDirectory(file);
        return  fileReadingMessageSource;
    }


    @Bean
    GenericSelector<File> pdfFileSelector(){
        return (source)->source.getName().endsWith(".pdf");
    }
//spring integration filter



    //we use thymeleaf view resolver if we want our dispatcher servlet to resolve view name of any thymelaf html file
    //we use classloaderTemplate resolver if we want dispatcher servlet  to resolve path for /template

    //changing default template folder path


    @Bean
    public ITemplateResolver getTemplateResolver(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;   //view resolver
    }


    @Bean
    @Primary  //if 2 message handler beans conflict set one of them primrary
    MessageHandler getMessageHandler(){
        return message -> repository.feedQueue((Book) message.getPayload());

    }  //writes polled book to queue

}
