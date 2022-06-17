package example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userResponse {
    private String userEmail;
    private String userPass;
    private String name;

    private String userGender;
    private String userLocation;
    private String userPhone;
    private String userBirth;
    public userResponse(User user) {
        this.userEmail = user.getUserEmail();
        this.userPass = user.getUserPass();
        this.name = user.getName();
        this.userGender = user.getUserGender();
        this.userLocation = user.getUserLocation();
        this.userPhone = user.getUserPhone();
        this.userBirth = user.getUserBirth();
    }
}