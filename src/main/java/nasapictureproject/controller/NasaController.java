package nasapictureproject.controller;


import nasapictureproject.dto.ImageResponse;
import nasapictureproject.service.NasaImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nasa")
public class NasaController {
    private final NasaImageService service;

    public NasaController(NasaImageService service) {
        this.service = service;
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage() {
        ImageResponse image = service.getImage();

        return ResponseEntity.ok()
                .contentType(image.getMediaType())
                .body(image.getData());
    }
}
