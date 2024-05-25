package com.example.demo.controller;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class ExcelController {

    @GetMapping("/open/excel")
    public ResponseEntity<byte[]> openExcel() throws IOException {
        // Load the Excel file from the resources folder
        Resource resource = new ClassPathResource("static/AreaList.xlsx");
        Path path = resource.getFile().toPath();
        byte[] excelBytes = Files.readAllBytes(path);

        // Set HTTP response headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=AreaList.xlsx");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}
