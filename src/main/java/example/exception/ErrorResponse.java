package example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ErrorResponse {
    private String error;



    public ErrorResponse(String error) {
        this.error = error;

    }
}
