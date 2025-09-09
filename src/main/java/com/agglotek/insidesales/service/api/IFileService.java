package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IFileService {
    public ResponseEntity<ApiResponse> uploadPDF(MultipartFile file, Integer clientId, String referenceNumber, String projectName, String type) throws IOException;

    public ResponseEntity<?> downloadPDF(Integer clientId, String referenceNumber, String projectName, String type) throws IOException;

    public ResponseEntity<ApiResponse> uploadAndExtractZip(MultipartFile file, Integer clientId, String referenceNumber, String projectName) throws IOException;

    List<String> listFiles(String referenceNumber, String projectName, Integer clientId) throws IOException;
}
