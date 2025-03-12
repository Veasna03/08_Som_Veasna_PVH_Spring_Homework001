package org.example.spring_homework001.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class FoundResponse {
    Boolean success;
    private String message;
    HttpStatus status;
    private LocalDateTime timestamp;

}
