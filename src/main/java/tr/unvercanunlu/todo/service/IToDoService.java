package tr.unvercanunlu.todo.service;

import org.springframework.validation.annotation.Validated;
import tr.unvercanunlu.todo.config.ErrorMessages;
import tr.unvercanunlu.todo.model.ToDo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface IToDoService {

    ToDo createToDo(
            @NotNull(message = ErrorMessages.NULL_TO_DO_TEXT)
            @NotEmpty(message = ErrorMessages.EMPTY_TO_DO_TEXT) String text,
            @NotNull(message = ErrorMessages.NULL_TO_DO_DONE) Boolean done
    );

    List<ToDo> searchToDos(String text, Boolean done);

    ToDo retrieveToDo(
            @NotNull(message = ErrorMessages.NULL_TO_DO_ID)
            @NotEmpty(message = ErrorMessages.EMPTY_TO_DO_ID) String id
    );

    ToDo updateToDo(
            @NotNull(message = ErrorMessages.NULL_TO_DO_ID)
            @NotEmpty(message = ErrorMessages.EMPTY_TO_DO_ID) String id,
            @NotNull(message = ErrorMessages.NULL_TO_DO_TEXT)
            @NotEmpty(message = ErrorMessages.EMPTY_TO_DO_TEXT) String text,
            @NotNull(message = ErrorMessages.NULL_TO_DO_DONE) Boolean done
    );

    void deleteToDo(
            @NotNull(message = ErrorMessages.NULL_TO_DO_ID)
            @NotEmpty(message = ErrorMessages.EMPTY_TO_DO_ID) String id
    );

    Boolean checkToDoExists(
            @NotNull(message = ErrorMessages.NULL_TO_DO_ID)
            @NotEmpty(message = ErrorMessages.EMPTY_TO_DO_ID) String id
    );

}
