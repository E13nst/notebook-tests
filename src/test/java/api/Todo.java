package api;

import com.fasterxml.jackson.annotation.JsonInclude;
import common.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {

    private Integer id;
    private Integer personId;
    private String description;

    public static String randomDescription() {
        return CommonUtils.randomString(20);
    }
}
