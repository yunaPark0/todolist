package example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userRequest {

    @Email(message = "이메일 형식이 맞지 않습니다.")
    @NotBlank(message = "이메일을 입력하십시오.")
    private String userEmail;

    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{8,20}$",
            message = "비밀번호는 8자리 이상 특수문자 1개 이상 포함되어야 합니다.")
    @NotBlank(message = "비밀번호를 입력하십시오.")
    private String userPass;

    @Pattern(regexp = "^[가-힣]{1,}$", message = "이름은 한글 1자 이상 입력해야 합니다.")
    @NotBlank(message = "이름을 입력하십시오.")
    private String name;

    private String userGender;
    private String userLocation;
    private String userPhone;
    private String userBirth;

}
