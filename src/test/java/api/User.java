package api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
    }
}
