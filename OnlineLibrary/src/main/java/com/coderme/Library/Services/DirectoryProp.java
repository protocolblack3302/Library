package com.coderme.Library.Services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties(prefix = "filescanner")
@Data
public class DirectoryProp {

    public String directory;
    public String encryptedPdfDirectory;

}
