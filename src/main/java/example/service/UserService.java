package example.service;

import example.model.User;
import example.model.userRequest;
import example.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public User add(userRequest request) {
        log.info("add11");
        User user = new User();
        user.setUserEmail(request.getUserEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPass(passwordEncoder.encode(request.getUserPass()));
        user.setUserName(request.getUserName());
        user.setUserGender(request.getUserGender());
        user.setUserLocation(request.getUserLocation());
        user.setUserPhone(request.getUserPhone());
        if (request.getUserBirth().equals("")){
            user.setUserBirth("");
        }
        else {
            try {
                String date = request.getUserBirth();
                SimpleDateFormat beforeDateFormat = new SimpleDateFormat("yyyymmdd");
                Date birth = beforeDateFormat.parse(date);
                String processDate = new SimpleDateFormat("yyyymmdd").format(birth);
                user.setUserBirth(processDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return this.userRepository.save(user);
    }

    public User searchById(String userEmail) {
        return  this.userRepository.findById(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public List<User> searchAll() {
        return this.userRepository.findAll();
    }

    public User updateById(String userEmail, userRequest request) {
        User user = this.searchById(userEmail);

        if(request.getUserPass() != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setUserPass(passwordEncoder.encode(request.getUserPass()));
        }
        if(request.getUserName() != null) {
            user.setUserName(request.getUserName());
        }
        if(request.getUserGender() != null) {
            user.setUserGender(request.getUserGender());
        }
        if(request.getUserLocation() != null) {
            user.setUserLocation(request.getUserLocation());
        }
        if(request.getUserPhone() != null) {
            user.setUserPhone(request.getUserPhone());
        }
        if(request.getUserBirth() != null) {
            user.setUserBirth(request.getUserBirth());
        }

        return this.userRepository.save(user);
    }

    public void deleteById(String userEmail) {
        this.userRepository.deleteById(userEmail);
    }

    public void deleteAll() {
        this.userRepository.deleteAll();
    }

}