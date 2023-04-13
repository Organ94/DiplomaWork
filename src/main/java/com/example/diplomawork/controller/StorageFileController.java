package com.example.diplomawork.controller;

import com.example.diplomawork.dto.request.EditFileNameRQ;
import com.example.diplomawork.dto.response.FileRS;
import com.example.diplomawork.service.StorageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
public class StorageFileController {

    @Autowired
    private StorageFileService storageFileService;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String filename,
            MultipartFile file
    ) {
        storageFileService.uploadFile(authToken, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String filename
    ) {
        storageFileService.deleteFile(authToken, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String filename
    ) {
        byte[] file = storageFileService.downloadFile(authToken, filename);
        return ResponseEntity.ok().body(new ByteArrayResource(file));
    }

    @PutMapping(value = "/file")
    public ResponseEntity<?> editFileName(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String filename,
            @RequestBody EditFileNameRQ editFileNameRQ
    ) {
        storageFileService.editFileName(authToken, filename, editFileNameRQ);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<FileRS> getAllFiles(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("limit") Integer limit
    ) {
        return storageFileService.getAllFiles(authToken, limit);
    }
}
