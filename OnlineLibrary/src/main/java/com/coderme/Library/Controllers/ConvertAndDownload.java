package com.coderme.Library.Controllers;

import com.coderme.Library.Converters.PdfUtilities;
import com.coderme.Library.Domains.User;
import com.coderme.Library.Services.DirectoryProp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;


@Controller
@Data
@RequestMapping(value = "/download")
@Slf4j
public class ConvertAndDownload {

    private final DirectoryProp directoryProp;

    @GetMapping
    public ResponseEntity<Object> downloadFile(@RequestParam(value = "link") String link , @AuthenticationPrincipal User loggedUser ) throws IOException {
        File input = new File(link);
        PDDocument document = encrypt(input,loggedUser.getFullname());
        File outputDirectory = new File(directoryProp.getEncryptedPdfDirectory()+"/"+loggedUser.getUsername());
        if(!outputDirectory.exists()){
            outputDirectory.mkdirs();
        }
        outputDirectory=new File(outputDirectory.getAbsolutePath()+"/"+input.getName());
        if(!outputDirectory.exists()) {
            document.save(outputDirectory);
        }
        document.close();
        HttpHeaders headers = new HttpHeaders();

        InputStreamResource resource = new InputStreamResource(new BufferedInputStream( new FileInputStream(outputDirectory)));
        headers.add("Content-Disposition",String.format("attachment; filename=\"%s\"",input.getName()));
           headers.add("Cache-Control","no-cache , no-store ,  must-revalidate");
           headers.add("pragma","no-cache");
           headers.add("expires","0");
           headers.setContentType(MediaType.APPLICATION_PDF);



        return ResponseEntity.ok().headers(headers).contentLength(outputDirectory.length()).body(resource);

    }

    public PDDocument encrypt(File file , String password) throws IOException {
        PdfUtilities utilities = new PdfUtilities(file);
        PDDocument document = utilities.document;
        int keyLength = 128;
        AccessPermission accessPermission = new AccessPermission();
        accessPermission.setCanPrint(false);
        StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy("",password,accessPermission);
        standardProtectionPolicy.setEncryptionKeyLength(keyLength);
        standardProtectionPolicy.setPermissions(accessPermission);
        document.protect(standardProtectionPolicy);
        return document;
    }

}
