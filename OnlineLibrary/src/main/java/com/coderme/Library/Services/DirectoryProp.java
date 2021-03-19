package com.coderme.Library.Services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "filescanner")
@Data
public class DirectoryProp {

    public static String directory="/Users/protocolblack/Downloads/book2";

}
