package id.ac.ui.cs.advprog.eshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CloudinaryServiceImplTest {
    
    private CloudinaryServiceImpl cloudinaryService;
    
    @BeforeEach
    void setUp() {
        // Use empty values for testing - real Cloudinary configuration would be in application.properties
        cloudinaryService = new CloudinaryServiceImpl("", "", "");
    }
    
    @Test
    void testIsPhoto_ValidPhotoContentType_ShouldReturnTrue() {
        MultipartFile jpegFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());
        MultipartFile pngFile = new MockMultipartFile("file", "test.png", "image/png", "test content".getBytes());
        MultipartFile gifFile = new MockMultipartFile("file", "test.gif", "image/gif", "test content".getBytes());
        
        assertTrue(cloudinaryService.isPhoto(jpegFile));
        assertTrue(cloudinaryService.isPhoto(pngFile));
        assertTrue(cloudinaryService.isPhoto(gifFile));
    }
    
    @Test
    void testIsPhoto_InvalidContentType_ShouldReturnFalse() {
        MultipartFile pdfFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
        MultipartFile textFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        
        assertFalse(cloudinaryService.isPhoto(pdfFile));
        assertFalse(cloudinaryService.isPhoto(textFile));
    }
    
    @Test
    void testIsPdf_ValidPdfContentType_ShouldReturnTrue() {
        MultipartFile pdfFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
        
        assertTrue(cloudinaryService.isPdf(pdfFile));
    }
    
    @Test
    void testIsPdf_InvalidContentType_ShouldReturnFalse() {
        MultipartFile jpegFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());
        MultipartFile textFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        
        assertFalse(cloudinaryService.isPdf(jpegFile));
        assertFalse(cloudinaryService.isPdf(textFile));
    }
    
    @Test
    void testIsValidFileType_PhotoFile_ShouldReturnTrue() {
        MultipartFile jpegFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());
        
        assertTrue(cloudinaryService.isValidFileType(jpegFile));
    }
    
    @Test
    void testIsValidFileType_PdfFile_ShouldReturnTrue() {
        MultipartFile pdfFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
        
        assertTrue(cloudinaryService.isValidFileType(pdfFile));
    }
    
    @Test
    void testIsValidFileType_InvalidFile_ShouldReturnFalse() {
        MultipartFile textFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        
        assertFalse(cloudinaryService.isValidFileType(textFile));
    }
    
    @Test
    void testUploadPhoto_InvalidFileType_ShouldThrowException() {
        MultipartFile pdfFile = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cloudinaryService.uploadPhoto(pdfFile);
        });
        
        assertEquals("File is not a valid photo format.", exception.getMessage());
    }
    
    @Test
    void testUploadPdf_InvalidFileType_ShouldThrowException() {
        MultipartFile jpegFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "test content".getBytes());
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cloudinaryService.uploadPdf(jpegFile);
        });
        
        assertEquals("File is not a valid PDF format.", exception.getMessage());
    }
    
    @Test
    void testUploadFile_InvalidFileType_ShouldThrowException() {
        MultipartFile textFile = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cloudinaryService.uploadFile(textFile);
        });
        
        assertEquals("File type not supported. Only photos and PDFs are allowed.", exception.getMessage());
    }
    
    @Test
    void testIsPhoto_NullContentType_ShouldReturnFalse() {
        MultipartFile fileWithNullContentType = new MockMultipartFile("file", "test.jpg", null, "test content".getBytes());
        
        assertFalse(cloudinaryService.isPhoto(fileWithNullContentType));
    }
    
    @Test
    void testIsPdf_NullContentType_ShouldReturnFalse() {
        MultipartFile fileWithNullContentType = new MockMultipartFile("file", "test.pdf", null, "test content".getBytes());
        
        assertFalse(cloudinaryService.isPdf(fileWithNullContentType));
    }
}