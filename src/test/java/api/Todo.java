package api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.CommonUtils;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Todo {

    private Integer id;

    private Integer personId;

    private String description;

    public Todo() {
    }

    public Todo(String description) {
        this.description = description;
    }

    public Todo(Integer id) {
        this.id = id;
    }

    public Todo randomDescription() {
        this.description = CommonUtils.randomString(20);
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPersonId() {
        return personId;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(id, todo.id) && Objects.equals(personId, todo.personId) && description.equals(todo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personId);
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", personId=" + personId +
                ", description='" + description + '\'' +
                '}';
    }
}
