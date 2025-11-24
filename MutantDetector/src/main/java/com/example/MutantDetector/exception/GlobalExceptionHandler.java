package com.example.MutantDetector.exception;

import com.example.MutantDetector.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores de validacion (@Valid, @ValidDnaSequence, etc.) → 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return buildResponse(HttpStatus.BAD_REQUEST, message, request);
    }

    //  Error calculando hash → 500
    @ExceptionHandler(HashGenerationException.class)
    public ResponseEntity<ErrorResponse> handleHash(HashGenerationException ex,
                                                    WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    //  Cualquier otra excepción no controlada → 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, WebRequest request) {
        String message = ex.getMessage() != null
                ? ex.getMessage()
                : "Ocurrio un error inesperado en el servidor.";
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, request);
    }


    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status,
                                                        String message,
                                                        WebRequest request) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(body, status);
    }
}
