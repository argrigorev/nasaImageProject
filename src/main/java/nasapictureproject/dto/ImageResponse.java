package nasapictureproject.dto;

import org.springframework.http.MediaType;

public class ImageResponse {
    private final byte[] data;
    private final MediaType mediaType;

    public ImageResponse(byte[] data, MediaType mediaType) {
        this.data = data;
        this.mediaType = mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
