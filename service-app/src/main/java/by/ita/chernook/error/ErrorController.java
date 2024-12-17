package by.ita.chernook.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpClientErrorException(HttpClientErrorException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatusCode().value(),
                "HttpClientErrorException",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpServerErrorException(HttpServerErrorException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getStatusCode().value(),
                "HttpServerErrorException",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "InternalServerError",
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}