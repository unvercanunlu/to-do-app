package tr.unvercanunlu.todo.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tr.unvercanunlu.todo.error.exception.ToDoNotFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ToDoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(ToDoNotFoundException exception) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("reason", exception.getMessage());

        Optional.ofNullable(exception.getId()).ifPresent(id -> {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("id", id);
            errorMap.put("data", dataMap);
        });

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMap);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException exception) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("reason", exception.getMessage());

        Map<String, String> dataMap = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> dataMap.put(error.getObjectName(), error.getDefaultMessage()));

        if (!dataMap.isEmpty()) {
            errorMap.put("data", dataMap);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMap);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ConstraintViolationException exception) {
        Map<String, Object> errorMap = new HashMap<>();

        List<String> messages = exception.getConstraintViolations()
                .stream()
                .filter(Objects::nonNull)
                .map(ConstraintViolation::getMessage)
                .filter(Objects::nonNull)
                .toList();

        if (!messages.isEmpty()) {
            errorMap.put("reason", messages);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMap);
    }

}
