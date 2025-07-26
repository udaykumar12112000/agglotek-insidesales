package com.agglotek.insidesales.service.api;

import com.agglotek.insidesales.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;

public interface IFileService {
    public ResponseEntity<ApiResponse> uploadPDF(MultipartFile file, Integer clientId, String projectNumber, String projectName) throws IOException;

    public ResponseEntity<Resource> downloadPDF(Integer clientId, String projectNumber, String projectName) throws IOException;
}
