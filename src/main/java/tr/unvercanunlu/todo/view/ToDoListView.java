package tr.unvercanunlu.todo.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import tr.unvercanunlu.todo.model.ToDo;
import tr.unvercanunlu.todo.service.IToDoService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Route(value = Strings.EMPTY)
public class ToDoListView extends VerticalLayout {

    private final IToDoService toDoService;

    @Autowired
    public ToDoListView(IToDoService toDoService) {
        this.toDoService = toDoService;

        this.prepareAddButton();
        this.loadToDoItems();
    }

    private void loadToDoItems() {
        List<ToDo> toDos = this.toDoService.searchToDos(null, null);

        toDos.forEach(toDo -> {
            HorizontalLayout toDoItemLayout = this.prepareToDoItem(toDo);
            this.add(toDoItemLayout);
        });
    }

    private void prepareAddButton() {
        Button addToDoButton = new Button();
        addToDoButton.setText("Add");
        addToDoButton.setIcon(VaadinIcon.PLUS.create());
        addToDoButton.setVisible(true);
        addToDoButton.addClickListener(addEvent -> {
            ToDo toDo = ToDo.builder().id(null).text(Strings.EMPTY).done(false).build();
            HorizontalLayout toDoItemLayout = this.prepareToDoItem(toDo);
            this.add(toDoItemLayout);
        });

        this.add(addToDoButton);
    }

    private HorizontalLayout prepareToDoItem(ToDo toDo) {
        HorizontalLayout toDoItemLayout = new HorizontalLayout();

        TextField toDoTextField = new TextField();
        toDoTextField.setVisible(true);
        Optional.ofNullable(toDo).map(ToDo::getText).ifPresent(toDoTextField::setValue);
        toDoItemLayout.add(toDoTextField);

        Checkbox toDoDoneCheckbox = new Checkbox();
        toDoDoneCheckbox.setVisible(true);
        Optional.ofNullable(toDo).map(ToDo::getDone).ifPresent(toDoDoneCheckbox::setValue);
        toDoItemLayout.add(toDoDoneCheckbox);

        Button deleteToDoButton = new Button();
        deleteToDoButton.setText("Delete");
        deleteToDoButton.setIcon(LumoIcon.CROSS.create());
        deleteToDoButton.setVisible(true);
        toDoItemLayout.add(deleteToDoButton);

        TextField toDoIdField = new TextField();
        toDoIdField.setVisible(false);
        Optional.ofNullable(toDo).map(ToDo::getId).ifPresent(toDoIdField::setValue);
        toDoItemLayout.add(toDoIdField);

        toDoTextField.addValueChangeListener(event -> {
            if (Objects.nonNull(toDoIdField.getValue()) && !toDoIdField.getValue().isEmpty()) {
                this.toDoService.updateToDo(toDoIdField.getValue(), event.getValue(), toDoDoneCheckbox.getValue());
            } else {
                ToDo created = this.toDoService.createToDo(event.getValue(), toDoDoneCheckbox.getValue());
                toDoIdField.setValue(created.getId());
            }
        });

        toDoDoneCheckbox.addValueChangeListener(event -> {
            if (Objects.nonNull(toDoIdField.getValue()) && !toDoIdField.getValue().isEmpty()) {
                this.toDoService.updateToDo(toDoIdField.getValue(), toDoTextField.getValue(), event.getValue());
            } else {
                ToDo created = this.toDoService.createToDo(toDoTextField.getValue(), event.getValue());
                toDoIdField.setValue(created.getId());
            }
        });

        deleteToDoButton.addClickListener(event -> {
            if (Objects.nonNull(toDoIdField.getValue()) && !toDoIdField.getValue().isEmpty()) {
                this.toDoService.deleteToDo(toDoIdField.getValue());
            }
            this.remove(toDoItemLayout);
        });

        return toDoItemLayout;
    }

}
