package MetodologiaDeSistema.Proyecto.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import MetodologiaDeSistema.Proyecto.feature.Direccion.dtos.response.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler{
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
            IllegalArgumentException ex,
            WebRequest request) {
        
        logger.warn("IllegalArgumentException: {}", ex.getMessage());
        
        ErrorResponseDto error = ErrorResponseDto.builder()
            .codigo("VALIDACION_ERROR")
            .mensaje(ex.getMessage())
            .timestamp(LocalDateTime.now())
            .path(request.getDescription(false).replace("uri=", ""))
            .status(HttpStatus.BAD_REQUEST.value())
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponseDto> handleDataIntegrity(
            DataIntegrityViolationException ex,
            WebRequest request) {
        
        logger.warn("Intento de crear dirección duplicada");
        
        ErrorResponseDto error = ErrorResponseDto.builder()
            .codigo("DIRECCION_DUPLICADA")
            .mensaje("Esta dirección ya existe para el cliente")
            .timestamp(LocalDateTime.now())
            .path(request.getDescription(false).replace("uri=", ""))
            .status(HttpStatus.CONFLICT.value())
            .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        Map<String, Object> response = new HashMap<>();
        Map<String, String> errores = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errores.put(error.getField(), error.getDefaultMessage())
        );
        
        response.put("codigo", "VALIDACION_ERROR");
        response.put("errores", errores);
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleGeneral(
            Exception ex,
            WebRequest request) {
        
        logger.error("Error no manejado", ex);
        
        ErrorResponseDto error = ErrorResponseDto.builder()
            .codigo("ERROR_INTERNO")
            .mensaje("Error interno del servidor")
            .timestamp(LocalDateTime.now())
            .path(request.getDescription(false).replace("uri=", ""))
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}