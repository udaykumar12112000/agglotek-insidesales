package com.agglotek.insidesales.service.impl;

import com.agglotek.insidesales.ApiResponse;
import com.agglotek.insidesales.constants.AppConstants;
import com.agglotek.insidesales.dao.entity.Client;
import com.agglotek.insidesales.repository.ClientRepository;
import com.agglotek.insidesales.service.api.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import org.springframework.core.io.Resource;

@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<ApiResponse> uploadPDF(MultipartFile file, Integer clientId, String referenceNumber, String projectName, String type) throws IOException {
        try {
            Optional<Client> clientOpt = clientRepository.findById(clientId);
            if (clientOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid clientId"));
            }

            String clientName = clientOpt.get().getName().replaceAll("\\s+", "_");
            String safeProjectName = projectName.replaceAll("\\s+", "_");

            String baseDir;
            String suffix;
            if ("po".equalsIgnoreCase(type)) {
                baseDir = AppConstants.PROJECT_BASE_DIRECTORY;
                suffix = "_PO.pdf";
            } else if ("scope_of_work".equalsIgnoreCase(type)) {
                baseDir = AppConstants.QUOTATION_BASE_DIRECTORY;
                suffix = "_scope_of_work.pdf";
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid type! Use 'po' or 'scope_of_work'"));
            }


            Path targetDir = Paths.get(baseDir, clientName, referenceNumber + "_" + safeProjectName);
            Files.createDirectories(targetDir);

            // Rename the file
            String renamedFile = referenceNumber + "_" + safeProjectName + suffix;
            Path targetPath = targetDir.resolve(renamedFile);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(new ApiResponse(true, "PDF uploaded successfully"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Upload failed!"));
        }
    }

    public ResponseEntity<Resource> downloadPDF(Integer clientId, String referenceNumber, String projectName, String type) throws IOException {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        String clientName = client.get().getName().replaceAll("\\s+", "_");
        String safeProjectName = projectName.replaceAll("\\s+", "_");

        String baseDir;
        String suffix;
        if ("po".equalsIgnoreCase(type)) {
            baseDir = AppConstants.PROJECT_BASE_DIRECTORY;
            suffix = "_PO.pdf";
        } else if ("scope_of_work".equalsIgnoreCase(type)) {
            baseDir = AppConstants.QUOTATION_BASE_DIRECTORY;
            suffix = "_scope_of_work.pdf";
        } else {
            throw new IllegalArgumentException("Invalid type! Use 'po' or 'scope_of_work'");
        }

        String folderName = referenceNumber + "_" + safeProjectName;
        String filename = folderName + suffix;

        Path filePath = Paths.get(baseDir, clientName, folderName, filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("PDF not found");
        }

        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}

