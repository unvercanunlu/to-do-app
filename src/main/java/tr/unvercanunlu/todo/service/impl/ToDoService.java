package tr.unvercanunlu.todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.unvercanunlu.todo.error.exception.ToDoNotFoundException;
import tr.unvercanunlu.todo.model.ToDo;
import tr.unvercanunlu.todo.repository.IToDoMongoRepository;
import tr.unvercanunlu.todo.service.IToDoService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoService implements IToDoService {

    @Autowired
    private final IToDoMongoRepository toDoRepository;

    @Override
    public ToDo createToDo(String text, Boolean done) {
        ToDo toDo = ToDo.builder().id(UUID.randomUUID().toString()).text(text).done(done).build();
        return this.toDoRepository.save(toDo);
    }

    @Override
    public List<ToDo> searchToDos(String text, Boolean done) {
        List<ToDo> toDos = this.toDoRepository.findAll();

        return toDos.stream()
                .filter(toDo -> Objects.nonNull(toDo) && Objects.nonNull(toDo.getText()) && Objects.nonNull(toDo.getDone()))
                .filter(toDo -> Objects.isNull(text) || toDo.getText().toLowerCase().trim().contains(text.trim().toLowerCase()))
                .filter(toDo -> Objects.isNull(done) || toDo.getDone().equals(done))
                .toList();
    }

    @Override
    public ToDo retrieveToDo(String id) {
        return this.toDoRepository.findById(id).orElseThrow(() -> new ToDoNotFoundException(id));
    }

    @Override
    public ToDo updateToDo(String id, String text, Boolean done) {
        ToDo toDo = this.retrieveToDo(id);
        toDo.setDone(done);
        toDo.setText(text);
        toDo = this.toDoRepository.save(toDo);
        return toDo;
    }

    @Override
    public void deleteToDo(String id) {
        this.checkToDoExists(id);
        this.toDoRepository.deleteById(id);
    }

    @Override
    public Boolean checkToDoExists(String id) {
        ToDo toDo = this.retrieveToDo(id);
        return Optional.ofNullable(toDo).isPresent();
    }

}
