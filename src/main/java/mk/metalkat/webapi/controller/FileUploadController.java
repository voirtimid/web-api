package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.service.CNCService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin("http://localhost:3000")
@RestController()
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {

    //Save the uploaded file to this folder
    @Value("${uploads.folder}")
    private String UPLOADED_FOLDER;

    private final CNCService cncService;

    @PostMapping("/{folderName}")
    public boolean singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("folderName") String folderName) {

        if (file.isEmpty()) {
            return false;
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + folderName + "/" + file.getOriginalFilename());
            Files.write(path, bytes);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @PostMapping("/cnc/{cncId}")
    public boolean singleFileUploadCNC(@RequestParam("file") MultipartFile file, @PathVariable("cncId") Long cncId) {

        if (file.isEmpty()) {
            return false;
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
