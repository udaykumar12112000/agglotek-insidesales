package com.agglotek.insidesales.controller;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.ApiConstants;
import com.agglotek.insidesales.service.api.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
// @CrossOrigin(
//         origins = "http://localhost:4200",
//         allowedHeaders = {"Content-Type", "Authorization", "X-Requested-With"},
//         methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS},
//         allowCredentials = "false" // set true only if you use cookies
// )

@CrossOrigin(
       origins = {"http://localhost:4200"},
       allowedHeaders = "*"
)
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private IFileService fileService;

    @PostMapping(ApiConstants.FILE_UPLOAD)
    public ResponseEntity<ApiResponse> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("clientId") Integer clientId,
            @RequestParam("referenceNumber") String referenceNumber,
            @RequestParam("projectName") String projectName,
            @RequestParam("type") String type) {

        try {
            return fileService.uploadPDF(file, clientId, referenceNumber, projectName, type);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Upload failed!"));

        }
    }

    @GetMapping(ApiConstants.FILE_DOWNLOAD)
    public ResponseEntity<?> download(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("referenceNumber") String referenceNumber,
            @RequestParam("projectName") String projectName,
            @RequestParam("type") String type) {

        try {
            return fileService.downloadPDF(clientId, referenceNumber, projectName, type);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Download failed!"));
        }
    }

    @PostMapping(ApiConstants.UPLOAD_FILES_IN_ZIP)
    public ResponseEntity<ApiResponse> uploadZip(@RequestParam("file") MultipartFile file,
                                            @RequestParam("clientId") Integer clientId,
                                            @RequestParam("referenceNumber") String referenceNumber,
                                            @RequestParam("projectName") String projectName) {
        try {
            return fileService.uploadAndExtractZip(file, clientId, referenceNumber, projectName);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Upload failed!"));
        }
    }

    @DeleteMapping(ApiConstants.DELETE_FILE)
    public ResponseEntity<ApiResponse> deleteFile(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("referenceNumber") String referenceNumber,
            @RequestParam("projectName") String projectName,
            @RequestParam("type") String type) {
        try {
            return fileService.deleteFile(clientId, referenceNumber, projectName, type);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Delete failed!"));
        }
    }

    @GetMapping(ApiConstants.LIST_FILES)
    public ResponseEntity<ApiResponse> listFiles(
            @RequestParam String referenceNumber,
            @RequestParam String projectName,
            @RequestParam Integer clientId) {
        try {
            List<String> fileNames = fileService.listFiles(referenceNumber, projectName, clientId);
            return ResponseEntity.ok(new ApiResponse(true, "Files listed successfully", fileNames));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Error listing files: " + e.getMessage()));
        }
    }

}

