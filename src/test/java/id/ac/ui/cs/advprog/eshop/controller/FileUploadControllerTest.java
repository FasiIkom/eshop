package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FileUploadController.class)
class FileUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void uploadFile_ValidPhoto_ShouldReturnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        
        Map<String, Object> result = Map.of("url", "http://example.com/image.jpg");
        when(cloudinaryService.uploadFile(any())).thenReturn(result);

        mockMvc.perform(multipart("/api/upload/file")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("http://example.com/image.jpg"));
    }

    @Test
    void uploadFile_EmptyFile_ShouldReturnBadRequest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.jpg", "image/jpeg", "".getBytes()
        );

        mockMvc.perform(multipart("/api/upload/file")
                .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Please select a file to upload"));
    }

    @Test
    void uploadPhoto_ValidPhoto_ShouldReturnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        
        Map<String, Object> result = Map.of("url", "http://example.com/image.jpg");
        when(cloudinaryService.uploadPhoto(any())).thenReturn(result);

        mockMvc.perform(multipart("/api/upload/photo")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("http://example.com/image.jpg"));
    }

    @Test
    void uploadPdf_ValidPdf_ShouldReturnOk() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "file", "test.pdf", "application/pdf", "test content".getBytes()
        );
        
        Map<String, Object> result = Map.of("url", "http://example.com/document.pdf");
        when(cloudinaryService.uploadPdf(any())).thenReturn(result);

        mockMvc.perform(multipart("/api/upload/pdf")
                .file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("http://example.com/document.pdf"));
    }

    @Test
    void validateFile_ValidPhotoContentType_ShouldReturnValid() throws Exception {
        mockMvc.perform(get("/api/upload/validate")
                .param("contentType", "image/jpeg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPhoto").value(true))
                .andExpect(jsonPath("$.isPdf").value(false))
                .andExpect(jsonPath("$.isValid").value(true));
    }

    @Test
    void validateFile_ValidPdfContentType_ShouldReturnValid() throws Exception {
        mockMvc.perform(get("/api/upload/validate")
                .param("contentType", "application/pdf"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPhoto").value(false))
                .andExpect(jsonPath("$.isPdf").value(true))
                .andExpect(jsonPath("$.isValid").value(true));
    }

    @Test
    void validateFile_InvalidContentType_ShouldReturnInvalid() throws Exception {
        mockMvc.perform(get("/api/upload/validate")
                .param("contentType", "text/plain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isPhoto").value(false))
                .andExpect(jsonPath("$.isPdf").value(false))
                .andExpect(jsonPath("$.isValid").value(false));
    }
}