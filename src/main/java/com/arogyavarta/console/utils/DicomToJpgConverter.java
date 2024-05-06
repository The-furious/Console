package com.arogyavarta.console.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

public class DicomToJpgConverter {

    public static MultipartFile convertDicomToJpg(MultipartFile dicomFile) throws IOException {
        String apiUrl = "http://localhost:5000/convert";

        // Prepare multipart request with DICOM file
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", dicomFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send POST request to Flask API
        RestTemplate restTemplate = new RestTemplate();
        byte[] jpgBytes = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, byte[].class).getBody();

        // Return MultipartFile with the same name as the input DICOM file
        String originalFilename = StringUtils.cleanPath(dicomFile.getOriginalFilename());
        String filename = originalFilename.replace(".DCM", ".jpg").replace(".dcm", ".jpg");

        // Create a new MultipartFile from byte array
        return new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return jpgBytes == null || jpgBytes.length == 0;
            }

            @Override
            public long getSize() {
                return jpgBytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return jpgBytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(jpgBytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                new FileOutputStream(dest).write(jpgBytes);
            }
        };
    }
}