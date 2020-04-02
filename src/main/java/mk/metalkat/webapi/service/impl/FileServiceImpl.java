package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.jpa.Cnc;
import mk.metalkat.webapi.repository.CNCRepository;
import mk.metalkat.webapi.service.FileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    //Save the uploaded file to this folder
    @Value("${uploads.folder}")
    private String UPLOADED_FOLDER;

    private final CNCRepository cncRepository;

    @Override
    public boolean copyFolders(String from, String to) {
        String filePathFrom = UPLOADED_FOLDER + from + "/";
        String filePathTo = UPLOADED_FOLDER + to + "/";

        File fileFrom = new File(filePathFrom);
        File fileTo = new File(filePathTo);

        try {
            FileUtils.copyDirectory(fileFrom, fileTo);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean renameFolders(String from, String to) {
        String filePathFrom = UPLOADED_FOLDER + from + "/";
        String filePathTo = UPLOADED_FOLDER + to + "/";

        File fileFrom = new File(filePathFrom);
        File fileTo = new File(filePathTo);

        return fileFrom.renameTo(fileTo);
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request, String folderName) throws IOException {
        Path filePath = Paths.get(UPLOADED_FOLDER + folderName + "/" + fileName).toAbsolutePath().normalize();
        Resource resource = new UrlResource(filePath.toUri());

        // Try to determine file's content type
        String contentType;
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Override
    public boolean singleFileUpload(MultipartFile file, String folderName) {
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

    @Override
    public String readFileFromDisk(Long cncId) {
        try {

            Cnc cnc = cncRepository.findById(cncId).orElseThrow(() -> new ModelNotFoundException("This CNC code does not exist!"));

            String filepath = UPLOADED_FOLDER + "cnc/" + cnc.getCncFilename();
            // Get the file and save it somewhere

            File file = new File(filepath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            StringBuilder sb = new StringBuilder();
            String line;
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
