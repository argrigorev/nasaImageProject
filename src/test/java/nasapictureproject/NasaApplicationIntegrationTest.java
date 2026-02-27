package nasapictureproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 8081)
@TestPropertySource(properties = {
        "nasa.api.base-url=http://localhost:8081",
        "nasa.api.key=test-key"
})
class NasaApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void service_ShouldReturnImage_WhenNasaReturnsImage() {
        stubFor(get(urlPathEqualTo("/planetary/apod"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                            {
                                "media_type": "image",
                                "url": "http://localhost:8081/test-image.jpg"
                            }
                            """)));

        stubFor(get("/test-image.jpg")
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "image/jpeg")
                        .withBody("fake-image".getBytes())));

        ResponseEntity<byte[]> response = restTemplate.getForEntity(
                "/api/nasa/image",
                byte[].class
        );

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }
}
