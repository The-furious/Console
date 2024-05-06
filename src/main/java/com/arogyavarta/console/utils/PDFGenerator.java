package com.arogyavarta.console.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.arogyavarta.console.dto.ImageDTO;
import com.arogyavarta.console.entity.Consultation;
import com.arogyavarta.console.entity.Prescription;
import com.arogyavarta.console.entity.Tests;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.TextAlignment;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PDFGenerator {

    public static MultipartFile generateReport(Consultation consultation, Prescription prescription, Tests test, List<ImageDTO> images, String originalFilename) {
        // Create a new ByteArrayOutputStream to store the PDF content
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Create a new PDF document
        PdfDocument pdf = new PdfDocument(new PdfWriter(outputStream));
        Document document = new Document(pdf, PageSize.A4);

        try {
            // Load fonts
            PdfFont font = PdfFontFactory.createFont();

            // Set global styles
            Style titleStyle = new Style().setFont(font).setFontSize(20).setBold().setFontColor(new DeviceRgb(68, 114, 196)).setTextAlignment(TextAlignment.CENTER).setMarginBottom(20);
            Style headingStyle = new Style().setFont(font).setFontSize(18).setBold().setFontColor(new DeviceRgb(0, 102, 204)).setMarginBottom(10);
            Style fieldStyle = new Style().setFont(font).setFontSize(14).setBold().setFontColor(new DeviceRgb(0, 0, 0));
            Style valueStyle = new Style().setFont(font).setFontSize(14).setFontColor(new DeviceRgb(51, 51, 51));

            ImageData imageData = ImageDataFactory.create("src/main/resources/icon.jpg");
            Image icon = new Image(imageData).setWidth(50);
            Paragraph title = new Paragraph().add(" ArogyaVartha").addStyle(titleStyle);
            document.add(title);

            // Add consultation details
            addSection(document, "Consultation Details", headingStyle);
            addContent(document, "Patient Name : ", consultation.getPatient().getName(), fieldStyle, valueStyle);
            addContent(document, "Doctor Name : ", consultation.getDoctor().getName(), fieldStyle, valueStyle);
            addContent(document, "Doctor Number : ", consultation.getDoctor().getContactNumber(), fieldStyle, valueStyle);
            addContent(document, "Lab Name : ", consultation.getDoctor().getContactNumber(), fieldStyle, valueStyle);

            // Add doctor remarks
            addSection(document, "Doctor Remarks", headingStyle);
            addContent(document, "", prescription.getImpression(), valueStyle, valueStyle);

            // Add prescription
            addSection(document, "Prescription", headingStyle);
            addContent(document, "", prescription.getPrescriptions(), valueStyle, valueStyle);

            // Add Test Details
            addSection(document, "Test Details", headingStyle);
            addContent(document, "Test Name :", test.getTestName(), fieldStyle, valueStyle);
            addContent(document, "Test Id :", test.getTestId().toString(), fieldStyle, valueStyle);
            for (ImageDTO image : images) {
                addImage(document, image.getImageUrl());

            }

            // Close the document
            document.close();

            // Calculate the hash value of the PDF content
            byte[] pdfBytes = outputStream.toByteArray();
            String hashValue = DigestUtils.md5DigestAsHex(pdfBytes);

            // Create a custom implementation of MultipartFile
            return new CustomMultipartFile(pdfBytes, hashValue, originalFilename);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of error
        } finally {
            try {
                // Close the ByteArrayOutputStream
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addSection(Document document, String title, Style style) {
        Paragraph sectionTitle = new Paragraph(title).addStyle(style).setMarginBottom(5);
        document.add(sectionTitle);
    }

    private static void addContent(Document document, String field, String value, Style fieldStyle, Style valueStyle) {
        Paragraph content = new Paragraph();
        if (field.length() > 0) {
            content.add(new Text(field).addStyle(fieldStyle));
        }
        content.add(new Text(value).addStyle(valueStyle));
        document.add(content);
    }

    // Custom implementation of MultipartFile
    private static class CustomMultipartFile implements MultipartFile {
        private final byte[] content;
        private final String hashValue;
        private final String originalFilename;

        public CustomMultipartFile(byte[] content, String hashValue, String originalFilename) {
            this.content = content;
            this.hashValue = hashValue;
            this.originalFilename = originalFilename;
        }

        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return originalFilename;
        }

        @Override
        public String getContentType() {
            return "application/pdf";
        }

        @Override
        public boolean isEmpty() {
            return content == null || content.length == 0;
        }

        @Override
        public long getSize() {
            return content != null ? content.length : 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return content;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(content);
        }

        @Override
        public void transferTo(File file) throws IOException, IllegalStateException {

        }

        public String getHashValue() {
            return hashValue;
        }
    }

    private static void addImage(Document document, URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            byte[] imageBytes = convertInputStreamToByteArray(inputStream);
            ImageData imageData = ImageDataFactory.create(imageBytes);
            Image image = new Image(imageData);
            document.add(image);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }
}
