package br.com.acme.sw.apis.planetapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    private OffsetDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private List<ErrorInfo> errors;
    private String path;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorInfo {
        private List<String> codes;
        private String defaultMessage;
        private String objectName;
        private String field;
    }
}

