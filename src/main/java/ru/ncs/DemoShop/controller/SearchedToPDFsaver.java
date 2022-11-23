package ru.ncs.DemoShop.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.controller.response.GetListResponse;
import ru.ncs.DemoShop.controller.response.GetProductResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SearchedToPDFsaver {
    public void saveSearchedToPdf(GetListResponse listResponse) throws IOException {
        int x = 20;
        int y = 750;
        int defaultCount=5;// Max Products per page
        int count = 0;
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.COURIER, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setLeading(12f);
        List<GetProductResponse> respList = listResponse.getProducts();

        GetProductResponse[] bigStr = new GetProductResponse[respList.size()];
        respList.toArray(bigStr);

        for (GetProductResponse product : bigStr) {
            if (count == defaultCount) {
                contentStream.endText();
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.COURIER, 10);
                contentStream.beginText();
                contentStream.newLineAtOffset(x, y);
                contentStream.setLeading(12f);
            }
            count++;

            contentStream.newLine();
            String[] str = product.toString().split(",");
            for (String field : str) {
                contentStream.newLine();
                contentStream.showText(" ");
                contentStream.showText(field);
            }
        }

        contentStream.endText();
        contentStream.close();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy_MM_dd__HH_mm");
        document.save("Searched_" + LocalDateTime.now().format(formatter) + ".pdf");
        document.close();
    }
}
