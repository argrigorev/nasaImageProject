package nasapictureproject.service;

import nasapictureproject.client.NasaClient;
import nasapictureproject.dto.ImageResponse;
import nasapictureproject.dto.NasaApodResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NasaImageServiceTest {

    @Mock
    private NasaClient nasaClient;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NasaImageService service;

    private NasaApodResponse apodResponse;
    private byte[] fakeImageBytes;

    @BeforeEach
    void setUp() {
        fakeImageBytes = "fakeImage".getBytes();
    }

    @Test
    void getImage_ShouldReturnImage_WhenMediaTypeIsImage() {
        apodResponse = new NasaApodResponse();
        apodResponse.setMedia_type("image");
        apodResponse.setUrl("https://test.com/image.jpg");

        when(nasaClient.getApod(any())).thenReturn(apodResponse);
        when(restTemplate.getForEntity(anyString(), eq(byte[].class)))
                .thenReturn(new ResponseEntity<>(fakeImageBytes, HttpStatus.OK));

        ImageResponse result = service.getImage();

        assertNotNull(result);
        assertArrayEquals(fakeImageBytes, result.getData());

        verify(nasaClient).getApod(any());
        verify(restTemplate).getForEntity("https://test.com/image.jpg",byte[].class);
    }

}
