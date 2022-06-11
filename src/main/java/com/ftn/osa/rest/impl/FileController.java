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

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        int startIndex = fileName.replaceAll("\\\\", "/").lastIndexOf("/");
        String uploadDir = "src/main/webapp/reactjs/public/images";
        FileUploadUtil.saveFile(uploadDir, fileName, file);


        return ResponseEntity.ok().build();
    }

}
