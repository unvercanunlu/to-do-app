package tr.unvercanunlu.todo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tr.unvercanunlu.todo.model.ToDo;

@Repository
public interface IToDoMongoRepository extends MongoRepository<ToDo, String> {
}
