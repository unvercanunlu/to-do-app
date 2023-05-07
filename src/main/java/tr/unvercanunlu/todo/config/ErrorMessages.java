package tr.unvercanunlu.todo.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public static final String NOT_FOUND_TO_DO = "To Do with {id} ID is not found.";

    public static final String NULL_TO_DO_TEXT = "To Do text cannot be null.";

    public static final String EMPTY_TO_DO_TEXT = "To Do text cannot be empty string.";

    public static final String NULL_TO_DO_DONE = "To Do done cannot be null.";

    public static final String NULL_TO_DO_ID = "To Do id cannot be null.";

    public static final String EMPTY_TO_DO_ID = "To Do id cannot be empty string.";

}
