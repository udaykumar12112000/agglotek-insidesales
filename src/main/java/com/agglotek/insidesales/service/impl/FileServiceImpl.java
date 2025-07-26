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

    public ResponseEntity<ApiResponse> uploadPDF(MultipartFile file, Integer clientId, String projectNumber, String projectName) throws IOException {
        Optional<Client> client = clientRepository.findById(clientId);
        try {
            Optional<Client> clientOpt = clientRepository.findById(clientId);
            if (clientOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid clientId"));
            }

            String clientName = clientOpt.get().getName().replaceAll("\\s+", "_");

            Path targetDir = Paths.get(AppConstants.BASE_DIRECTORY, clientName, projectNumber + "_" + projectName.replaceAll("\\s+", "_"));
            Files.createDirectories(targetDir);

            // Rename the file
            String safeProjectName = projectName.replaceAll("\\s+", "_");
            String renamedFile = projectNumber + "_" + safeProjectName + "_PO.pdf";
            Path targetPath = targetDir.resolve(renamedFile);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(new ApiResponse(true, "PDF uploaded successfully"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Upload failed! " + e.getMessage()));
        }
    }

    public ResponseEntity<Resource> downloadPDF(Integer clientId, String projectNumber, String projectName) throws IOException {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        String clientName = client.get().getName().replaceAll("\\s+", "_");
        String projectFolder = projectNumber + "_" + projectName.replaceAll("\\s+", "_");
        String filename = projectFolder + "_PO.pdf";

        Path filePath = Paths.get(AppConstants.BASE_DIRECTORY, clientName, projectFolder, filename);
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

