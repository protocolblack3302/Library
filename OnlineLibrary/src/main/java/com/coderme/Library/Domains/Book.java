package com.coderme.Library.Domains;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Books")
public class Book implements Serializable
{
   @Column(unique = true , nullable = false)
   @Id
    private String bookTitle;
    private float version;
    private String author;
    private int pages;
    @Column(unique = true ,nullable = false )
    private String link;

}
