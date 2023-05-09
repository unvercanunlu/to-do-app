package tr.unvercanunlu.todo.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String NOT_FOUND_TODO = "ToDo with {id} ID is not found.";

    public static final String NULL_TODO_TEXT = "ToDo text cannot be null.";

    public static final String EMPTY_TODO_TEXT = "ToDo text cannot be empty string.";

    public static final String NULL_TODO_DONE = "ToDo done cannot be null.";

    public static final String NULL_TODO_ID = "ToDo id cannot be null.";

    public static final String EMPTY_TODO_ID = "ToDo id cannot be empty string.";

}
