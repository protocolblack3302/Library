package com.coderme.Library.Domains;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Component
public class SearchObject {
    @NotBlank
    @Size(min=3,message = " please search a string > 3")
    private String searchString;
    private String string;

}
