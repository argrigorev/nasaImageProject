package nasapictureproject.client;

import nasapictureproject.dto.NasaApodResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "nasaClient",
        url = "${nasa.api.base-url}"
)
public interface NasaClient {

    @GetMapping("/planetary/apod")
    NasaApodResponse getApod(
            @RequestParam("api_key") String apiKey
    );

}
