package tr.unvercanunlu.todo.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tr.unvercanunlu.todo.config.ApiConfig;
import tr.unvercanunlu.todo.controller.IToDoRestController;
import tr.unvercanunlu.todo.model.ToDo;
import tr.unvercanunlu.todo.service.IToDoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.TODO_API)
public class ToDoRestController implements IToDoRestController {

    @Autowired
    private final IToDoService toDoService;

    @Override
    @PostMapping
    public ResponseEntity<ToDo> createToDo(
            @RequestParam(name = "text", defaultValue = "") String text,
            @RequestParam(name = "done", defaultValue = "false") Boolean done
    ) {
        ToDo toDo = this.toDoService.createToDo(text, done);
        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDo);
    }

    @Override
    @GetMapping(path = "/search")
    public ResponseEntity<List<ToDo>> searchToDos(
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "done", required = false) Boolean done
    ) {
        List<ToDo> toDos = this.toDoService.searchToDos(text, done);
        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDos);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<ToDo> retrieveToDo(@PathVariable(name = "id") String id) {
        ToDo toDo = this.toDoService.retrieveToDo(id);
        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDo);
    }

    @Override
    @PutMapping(path = "/{id}")
    public ResponseEntity<ToDo> updateToDo(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "text", defaultValue = "") String text,
            @RequestParam(name = "done", defaultValue = "false") Boolean done
    ) {
        ToDo toDo = this.toDoService.updateToDo(id, text, done);
        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(toDo);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable(name = "id") String id) {
        this.toDoService.deleteToDo(id);
        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @Override
    @RequestMapping(path = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> checkToDoExists(@PathVariable(name = "id") String id) {
        this.toDoService.checkToDoExists(id);
        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
