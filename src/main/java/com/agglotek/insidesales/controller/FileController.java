package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.service.api.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping(ApiConstants.PROJECT_PO_UPLOAD)
    public ResponseEntity<ApiResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("clientId") Integer clientId,
            @RequestParam("projectNumber") String projectNumber,
            @RequestParam("projectName") String projectName) {

        try {
            return fileService.uploadPDF(file, clientId, projectNumber, projectName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Upload failed!"));

        }
    }

    @GetMapping(ApiConstants.PROJECT_PO_DOWNLOAD)
    public ResponseEntity<?> download(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("projectNumber") String projectNumber,
            @RequestParam("projectName") String projectName) {

        try {
            return fileService.downloadPDF(clientId, projectNumber, projectName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Download failed!"));
        }
    }
}

