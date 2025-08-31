package id.ac.ui.cs.advprog.eshop.service;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<String, Object> uploadFile(MultipartFile file) throws IOException;
    Map<String, Object> uploadPhoto(MultipartFile file) throws IOException;
    Map<String, Object> uploadPdf(MultipartFile file) throws IOException;
    boolean isValidFileType(MultipartFile file);
    boolean isPhoto(MultipartFile file);
    boolean isPdf(MultipartFile file);
}