package com.coderme.Library.Converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Slf4j
public class PdfUtilities {
    private final File sourceFile;
    private final PDDocument document;

    public PdfUtilities(File sourceFile) throws IOException {
        this.sourceFile = sourceFile;
        this.document = PDDocument.load(sourceFile);
    }

    public void closeDocument(){
            try {
                document.close();
            } catch (IOException e) {
                log.warn("document cant be closed");
            }
    }

    public BufferedImage convertToThumbnail() {
        try {
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                return pdfRenderer.renderImage(0);

        }catch (IOException file) {
            log.info("could not convert pdf to thumbnail");
            return null;

        }
    }
        public String getTitle(){
            return sourceFile.getName().replace(".pdf","");

    }


    public  String getAuthor(){
        return document.getDocumentInformation().getAuthor();
    }
    public  int getPages(){
        return document.getNumberOfPages();
    }

    public float getVersion(){
        return document.getVersion();

    }


}
