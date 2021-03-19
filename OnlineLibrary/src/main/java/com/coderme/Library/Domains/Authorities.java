package com.coderme.Library.Domains;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Authorities {
    @Id
    private String username;
    @Column(nullable = false)
    private final String authority="ROLE_USER";

}
