package ru.ncs.DemoShop.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import ru.ncs.DemoShop.model.ProductCategoryEnum;
import ru.ncs.DemoShop.service.data.ProductDTO;

@Component
public class SearchResultSaver {
    private XSSFSheet sheet;
    private XSSFWorkbook workbook;
    public void saveSearchedToPdf(List<ProductDTO> listResponse) throws IOException {
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

        ProductDTO[] bigStr = new ProductDTO[listResponse.size()];
        listResponse.toArray(bigStr);

        for (ProductDTO product : bigStr) {
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

    public void saveSearchedToXls(List<ProductDTO> listResponse) throws IOException {
       // List<GetProductResponse> productList = listResponse.getProducts();
        workbook = new XSSFWorkbook();

        sheet = workbook.createSheet("Products");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "Category", style);
        createCell(row, 3, "Description", style);
        createCell(row, 4, "Price", style);
        createCell(row, 5, "Amount", style);
        createCell(row, 6, "Availability", style);
        createCell(row, 7, "Amount Updated At", style);

        int rowCount = 1;
        font.setFontHeight(14);
        style.setFont(font);
        for (ProductDTO product :listResponse) {
            row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, product.getId(), style);
            createCell(row, columnCount++, product.getName(), style);
            createCell(row, columnCount++, product.getCategory(), style);
            createCell(row, columnCount++, product.getDescription(), style);
            createCell(row, columnCount++, product.getPrice(), style);
            createCell(row, columnCount++, product.getAmount(), style);
            createCell(row, columnCount++, product.isAvailability(), style);
            createCell(row, columnCount++, product.getAmountUpdatedAt(), style);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy_MM_dd__HH_mm");
        FileOutputStream fileOut = new FileOutputStream("Searched_" + LocalDateTime.now().format(formatter) + ".xls");
        workbook.write(fileOut);
        workbook.close();
        fileOut.close();
    }
    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof UUID) {
            cell.setCellValue(String.valueOf(valueOfCell));
        } else if (valueOfCell instanceof Double) {
            cell.setCellValue((Double) valueOfCell);
        } else if (valueOfCell instanceof ProductCategoryEnum) {
            cell.setCellValue(String.valueOf(valueOfCell));
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else if (valueOfCell instanceof LocalDateTime) {
            cell.setCellValue((LocalDateTime) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }
}
