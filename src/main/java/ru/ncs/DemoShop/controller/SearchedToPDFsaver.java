package ru.ncs.DemoShop.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetListResponse;

import java.io.IOException;

@Component
public class SearchedToPDFsaver {
public void saveSearchedToPdf(GetListResponse listResponse) throws IOException {
    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);

    PDPageContentStream contentStream = new PDPageContentStream(document, page);

    contentStream.setFont(PDType1Font.COURIER, 12);
    contentStream.beginText();
    contentStream.showText("Hello World");
    contentStream.endText();
    contentStream.close();

    document.save("pdfBoxHelloWorld.pdf");
    document.close();
}
}
