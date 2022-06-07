package example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userRequest {

    @Email
    @NotBlank
    private String userEmail;

    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,20}$")
    @NotBlank
    private String userPass;

    @NotBlank
    @Pattern(regexp = "^[가-힣]{1,}$")
    private String userName;

    private String userGender;
    private String userLocation;
    private String userPhone;
    private String userBirth;

}
