package az.phonebook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseDto {

    private int status;

    private String error;

    private String message;

    private String errorDetail;

    private String path;

    private String timestamp;

}
