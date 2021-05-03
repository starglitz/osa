package com.ftn.osa.rest.impl;

import com.ftn.osa.service.impl.FileUploadUtil;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@CrossOrigin
@RestController
public class FileController {

    //private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    //@PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile multipartFile) throws IOException {
        //logger.info(String.format("File name '%s' uploaded successfully.", file.getOriginalFilename()));

        String fileName = multipartFile.getOriginalFilename();
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        //fileName = fileName.substring(startIndex + 1);
        //book.setPicturePath(fileName);
        //System.out.println(book);

        //Book save = bookService.save(book);
        String uploadDir = "src/main/webapp/reactjs/public/images";
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        return ResponseEntity.ok().build();
    }
    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN', 'CUSTOMER')")
    @GetMapping(value = "/testtest",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity getAllArticles() {
        return new ResponseEntity("ovo moze", HttpStatus.OK);
    }
}
