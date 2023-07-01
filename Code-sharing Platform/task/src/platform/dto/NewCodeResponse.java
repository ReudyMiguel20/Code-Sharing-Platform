package platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewCodeResponse {

    @JsonProperty(value = "id")
    private String id;

    public NewCodeResponse() {
    }

    public NewCodeResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
