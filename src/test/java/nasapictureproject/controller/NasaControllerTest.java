package nasapictureproject.controller;

import nasapictureproject.dto.ImageResponse;
import nasapictureproject.service.NasaImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NasaController.class)
public class NasaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NasaImageService service;

    @Test
    void getImage_shouldReturnImage_whenServiceReturnsImage() throws Exception {
        byte[] imageData = "fakeImageData".getBytes();
        ImageResponse mockResponse = new ImageResponse(imageData, MediaType.IMAGE_JPEG);

        when(service.getImage()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/nasa/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageData));
    }

    @Test
    void getImage_shouldReturnError_whenServiceThrowsException() throws Exception {
        when(service.getImage()).thenThrow(new RuntimeException("NASA API error"));

        mockMvc.perform(get("/api/nasa/image"))
                .andExpect(status().is5xxServerError());
    }
}
