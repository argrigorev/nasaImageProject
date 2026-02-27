package nasapictureproject.service;


import nasapictureproject.client.NasaClient;
import nasapictureproject.dto.ImageResponse;
import nasapictureproject.dto.NasaApodResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaImageService {

    private final NasaClient nasaClient;
    private final RestTemplate restTemplate;
    private final String apiKey;

    public NasaImageService(
            NasaClient nasaClient,
            RestTemplate restTemplate,
            @Value("${nasa.api.key}") String apiKey
    ) {
        this.nasaClient = nasaClient;
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public ImageResponse getImage() {
        NasaApodResponse apod = nasaClient.getApod(apiKey);

        String imageUrl;

        if ("image".equals(apod.getMedia_type())) {
            imageUrl = apod.getUrl();
        } else if ("video".equals(apod.getMedia_type())) {
            imageUrl = extractYoutubePreview(apod.getUrl());
        } else {
            throw new IllegalStateException("Unsupported media type");
        }

        return downloadImage(imageUrl);
    }

    private String extractYoutubePreview(String youtubeUrl) {
        String videoId = youtubeUrl.substring(youtubeUrl.lastIndexOf("/") + 1);
        return "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
    }

    private ImageResponse downloadImage(String url) {
        ResponseEntity<byte[]> response =
                restTemplate.getForEntity(url, byte[].class);

        return new ImageResponse(
                response.getBody(),
                response.getHeaders().getContentType()
        );
    }
}