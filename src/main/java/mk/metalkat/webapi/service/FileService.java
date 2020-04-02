package mk.metalkat.webapi.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface FileService {

    boolean copyFolders(String from, String to);

    boolean renameFolders(String from, String to);

    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request, String folderName) throws IOException;

    boolean singleFileUpload(MultipartFile file, String folderName);

    String readFileFromDisk(Long cncId);
}
