package tr.unvercanunlu.todo.error.exception;

import lombok.Getter;
import tr.unvercanunlu.todo.config.ErrorMessages;

public class ToDoNotFoundException extends RuntimeException {

    @Getter
    private final String id;

    public ToDoNotFoundException(String id) {
        super(ErrorMessages.NOT_FOUND_TODO.replace("{id}", id));
        this.id = id;
    }

}
