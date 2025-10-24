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

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

            String baseDir = "";
            String fileName;
            String folderName = referenceNumber + "_" + safeProjectName;

            if (referenceNumber.startsWith("PO")) {
                baseDir = AppConstants.PROJECT_BASE_DIRECTORY;
            } else if (referenceNumber.startsWith("QO")) {
                baseDir = AppConstants.QUOTATION_BASE_DIRECTORY;
            } else if (referenceNumber.startsWith("INV")) {
                baseDir = AppConstants.INVOICE_BASE_DIRECTORY;
            }

            if ("po".equalsIgnoreCase(type)) {
                fileName = "_PO.pdf";
            } else if ("scope_of_work".equalsIgnoreCase(type)) {
                fileName = "_scope_of_work.pdf";
            } else if ("proposal".equalsIgnoreCase(type)) {
                fileName = "_proposal.pdf";
            } else if ("scope_of_work_cor".equalsIgnoreCase(type)) {
                fileName = "_scope_of_work_cor.pdf";
            } else if ("proposal_cor".equalsIgnoreCase(type)) {
                fileName = "_proposal_cor.pdf";
            } else if ("invoice".equalsIgnoreCase(type)) {
                fileName = "_invoice.pdf";
            } else if ("purchase_order".equalsIgnoreCase(type)) {
                fileName = "_purchase_order.pdf";
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, "Invalid type! Use 'po' or 'scope_of_work' or 'proposal'"));
            }

            Path clientDir = Paths.get(baseDir, clientName);
            if (Files.notExists(clientDir)) {
                Files.createDirectories(clientDir);
            }

            Path targetDir = clientDir.resolve(folderName);
            if (Files.notExists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path targetFile = targetDir.resolve(folderName + fileName);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(new ApiResponse(true, "PDF uploaded successfully"));


        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Upload failed!"));
        }
    }

    public ResponseEntity<?> downloadPDF(Integer clientId, String referenceNumber, String projectName, String type) throws IOException {
        Optional<Client> client = clientRepository.findById(clientId);
        if (client.isEmpty()) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        String clientName = client.get().getName().replaceAll("\\s+", "_");
        String safeProjectName = projectName.replaceAll("\\s+", "_");
        String folderName = referenceNumber + "_" + safeProjectName;

        String baseDir = "";
        String filename;


        if (referenceNumber.startsWith("PO")) {
            baseDir = AppConstants.PROJECT_BASE_DIRECTORY;
        } else if (referenceNumber.startsWith("QO")) {
            baseDir = AppConstants.QUOTATION_BASE_DIRECTORY;
        } else if (referenceNumber.startsWith("INV")) {
            baseDir = AppConstants.INVOICE_BASE_DIRECTORY;
        }

        if(type == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "Invalid type! Use 'po' or 'scope_of_work' or 'proposal'"));
        }
        else if ("po".equalsIgnoreCase(type)) {
            filename = folderName + "_PO.pdf";
        } else if ("scope_of_work".equalsIgnoreCase(type)) {
            filename = folderName + "_scope_of_work.pdf";
        } else if ("proposal".equalsIgnoreCase(type)) {
            filename = folderName + "_proposal.pdf";
        } else if ("scope_of_work_cor".equalsIgnoreCase(type)) {
            filename = folderName + "_scope_of_work_cor.pdf";
        } else if ("proposal_cor".equalsIgnoreCase(type)) {
            filename = folderName + "_proposal_cor.pdf";
        } else if ("invoice".equalsIgnoreCase(type)) {
            filename = folderName + "_invoice.pdf";
        } else if ("purchase_order".equalsIgnoreCase(type)) {
            filename = folderName + "_purchase_order.pdf";
        }
        else {
            filename = type;
        }

        Path filePath = Paths.get(baseDir, clientName, folderName, filename);
        if (!Files.exists(filePath)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "File not found : "+ filename));
        }

        Resource resource = new UrlResource(filePath.toUri());

        // Encode filename properly for HTTP header
        String encodedFileName = URLEncoder.encode(resource.getFilename(), StandardCharsets.UTF_8.toString())
                .replace("+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"")
                .body(resource);
    }

    public ResponseEntity<ApiResponse> uploadAndExtractZip(MultipartFile file, Integer clientId, String referenceNumber, String projectName) throws IOException {

        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Invalid clientId"));
        }

        String clientName = clientOpt.get().getName().replaceAll("\\s+", "_");
        String safeProjectName = projectName.replaceAll("\\s+", "_");

        String baseDir = "";
        String folderName = referenceNumber + "_" + safeProjectName;
        if (referenceNumber.startsWith("PO")) {
            baseDir = AppConstants.PROJECT_BASE_DIRECTORY;
        } else if (referenceNumber.startsWith("QO")) {
            baseDir = AppConstants.QUOTATION_BASE_DIRECTORY;
        } else if (referenceNumber.startsWith("INV")) {
            baseDir = AppConstants.INVOICE_BASE_DIRECTORY;
        }

        Path clientDir = Paths.get(baseDir, clientName);

        if (Files.notExists(clientDir)) {
            Files.createDirectories(clientDir);
        }

        Path targetDir = clientDir.resolve(folderName);
        if (Files.notExists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        Path tempZipPath = targetDir.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), tempZipPath, StandardCopyOption.REPLACE_EXISTING);

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(tempZipPath.toFile()))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {

                // Skip root folder entries inside the zip
                if (zipEntry.isDirectory()) {
                    zis.closeEntry();
                    continue;
                }

                // Extract only files into targetDir
                Path newFilePath = targetDir.resolve(Paths.get(zipEntry.getName()).getFileName()).normalize();

                // Security check: prevent Zip Slip
                if (!newFilePath.startsWith(targetDir)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ApiResponse(false, "Invalid entry: " + zipEntry.getName()));
                }

                // Write file content
                try (OutputStream fos = Files.newOutputStream(newFilePath)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
            }
        }

        // delete the uploaded ZIP after extraction
        Files.deleteIfExists(tempZipPath);

        List<String> fileNames = new ArrayList<>();
        try (Stream<Path> walk = Files.list(targetDir)) {
            fileNames = walk.filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(new ApiResponse(true, "Files uploaded successfully", fileNames));
    }

    public List<String> listFiles(String referenceNumber, String projectName, Integer clientId) throws IOException {

        List<String> fileNames = new ArrayList<>();
        if(clientId == null)
            return new ArrayList<>();
        Optional<Client> clientOpt = clientRepository.findById(clientId);
        if (clientOpt.isEmpty()) {
            return new ArrayList<>();
        }

        String clientName = clientOpt.get().getName().replaceAll("\\s+", "_");        String safeProjectName = projectName.replaceAll("\\s+", "_");
        String folderName = referenceNumber + "_" + safeProjectName;

        String baseDir;
        if (referenceNumber.startsWith("PO")) {
            baseDir = AppConstants.PROJECT_BASE_DIRECTORY;
        } else if (referenceNumber.startsWith("QO")) {
            baseDir = AppConstants.QUOTATION_BASE_DIRECTORY;
        } else if (referenceNumber.startsWith("INV")) {
            baseDir = AppConstants.INVOICE_BASE_DIRECTORY;
        }else {
            throw new IllegalArgumentException("Invalid reference number. Must start with PO or QO");
        }

        Path targetDir = Paths.get(baseDir, clientName, folderName);

//        if (!Files.exists(targetDir) || !Files.isDirectory(targetDir)) {
//            throw new FileNotFoundException("Folder not found: " + targetDir.toString());
//        }

        // list only files, not directories
        if(Files.exists(targetDir)) {
            try (Stream<Path> walk = Files.list(targetDir)) {
                fileNames = walk.filter(Files::isRegularFile)
                        .map(p -> p.getFileName().toString())
                        .collect(Collectors.toList());
            }
        }
        return fileNames;
    }
}

