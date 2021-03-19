package com.coderme.Library.Converters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class BookThumbnailTransformer implements GenericTransformer<String, BufferedImage> {
    @Override
    public BufferedImage transform(String sourceFile) {
        File file = new File(sourceFile);
        PdfUtilities pdfUtilities = null;
        BufferedImage image=null;
        try {
            pdfUtilities = new PdfUtilities(file);
            image = pdfUtilities.convertToThumbnail();
            log.info("====== writing ("+file.getName().replace(".pdf",".png )")+ "to file system ===== ");
            ImageIO.write(image,"png", new File(sourceFile.replace(".pdf", ".png")));
            log.info("==================    Done !  ====================");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pdfUtilities!=null){
                pdfUtilities.closeDocument();
            }
        }
        return image;
    }
}
