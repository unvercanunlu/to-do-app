package tr.unvercanunlu.todo.error.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tr.unvercanunlu.todo.error.exception.ToDoNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ToDoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleToDoNotFoundException(ToDoNotFoundException exception) {
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("reason", exception.getMessage());

        Optional.ofNullable(exception.getId()).ifPresent(postId -> {
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("postId", postId);
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

}
