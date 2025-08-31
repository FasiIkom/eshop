package id.ac.ui.cs.advprog.eshop.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    
    private final Cloudinary cloudinary;
    
    private static final List<String> PHOTO_CONTENT_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp"
    );
    
    private static final List<String> PDF_CONTENT_TYPES = Arrays.asList(
        "application/pdf"
    );
    
    public CloudinaryServiceImpl(
            @Value("${cloudinary.cloud-name:}") String cloudName,
            @Value("${cloudinary.api-key:}") String apiKey,
            @Value("${cloudinary.api-secret:}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
    }

    @Override
    public Map<String, Object> uploadFile(MultipartFile file) throws IOException {
        if (!isValidFileType(file)) {
            throw new IllegalArgumentException("File type not supported. Only photos and PDFs are allowed.");
        }
        
        if (isPhoto(file)) {
            return uploadPhoto(file);
        } else if (isPdf(file)) {
            return uploadPdf(file);
        } else {
            throw new IllegalArgumentException("File type not supported.");
        }
    }

    @Override
    public Map<String, Object> uploadPhoto(MultipartFile file) throws IOException {
        if (!isPhoto(file)) {
            throw new IllegalArgumentException("File is not a valid photo format.");
        }
        
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "resource_type", "image",
            "folder", "photos"
        ));
    }

    @Override
    public Map<String, Object> uploadPdf(MultipartFile file) throws IOException {
        if (!isPdf(file)) {
            throw new IllegalArgumentException("File is not a valid PDF format.");
        }
        
        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "resource_type", "raw",
            "folder", "documents"
        ));
    }

    @Override
    public boolean isValidFileType(MultipartFile file) {
        return isPhoto(file) || isPdf(file);
    }

    @Override
    public boolean isPhoto(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && PHOTO_CONTENT_TYPES.contains(contentType.toLowerCase());
    }

    @Override
    public boolean isPdf(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && PDF_CONTENT_TYPES.contains(contentType.toLowerCase());
    }
}