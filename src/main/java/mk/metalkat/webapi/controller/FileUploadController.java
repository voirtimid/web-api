package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Cnc;
import mk.metalkat.webapi.service.CNCService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            String filepath = UPLOADED_FOLDER + folderName + "/";
            File f = new File(filepath);
            if (Files.notExists(Paths.get(filepath))) {
                boolean mkdirs = f.mkdirs();
                if (!mkdirs) {
                    return false;
                }
            }

            Path path = Paths.get(filepath + file.getOriginalFilename());
            Files.write(path, bytes);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @GetMapping("/{cncId}")
    public String readFileFromDisk(@PathVariable("cncId") Long cncId) {
        try {

            Cnc cnc = cncService.getCNC(cncId);

            String filepath = UPLOADED_FOLDER + "cnc/" + cnc.getCncFilename();
            // Get the file and save it somewhere

            File file = new File(filepath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "false";
    }
}
