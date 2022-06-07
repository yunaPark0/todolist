package example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String userEmail;

    @Column(nullable = false)
    private String userPass;

    @Column(nullable = false)
    private String userName;

    private String userGender;
    private String userLocation;
    private String userPhone;
    private String userBirth;

}
