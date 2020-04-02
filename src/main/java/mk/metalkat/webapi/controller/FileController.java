package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.43.211:3000"})
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/copy/{from}/{to}")
    public boolean copyFolders(@PathVariable("from") String from, @PathVariable("to") String to) {
        return fileService.copyFolders(from, to);
    }

    @GetMapping("/rename/{from}/{to}")
    public boolean renameFolders(@PathVariable("from") String from, @PathVariable("to") String to) {
        return fileService.renameFolders(from, to);
    }

    @GetMapping("/downloadFile/{folderName}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request, @PathVariable("folderName") String folderName) throws IOException {

        return fileService.downloadFile(fileName, request, folderName);
    }

    @PostMapping("/{folderName}")
    public boolean singleFileUpload(@RequestParam("file") MultipartFile file, @PathVariable("folderName") String folderName) {
        return fileService.singleFileUpload(file, folderName);
    }

    @GetMapping("/{cncId}")
    public String readFileFromDisk(@PathVariable("cncId") Long cncId) {
        return fileService.readFileFromDisk(cncId);
    }
}
