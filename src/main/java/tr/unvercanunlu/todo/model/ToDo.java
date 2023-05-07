package tr.unvercanunlu.todo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document("toDo")
public class ToDo implements Serializable {

    @Id
    private String id;

    private String text;

    private Boolean done;

}
