package example.service;

import example.exception.BadRequestException;
import example.exception.EmailLoginFailedExc;
import example.exception.Exception1;
import example.exception.Exception2;
import example.model.User;
import example.model.userRequest;
import example.model.userResponse;
import example.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public User add(userRequest request) throws BadRequestException{
        log.info("add11");
        User user = new User();

        //이메일
        boolean existEmail = userRepository.existsByUserEmail(request.getUserEmail());
        if (existEmail) {
            throw new BadRequestException();
        }
        else {
            user.setUserEmail(request.getUserEmail());
        }

        //비밀번호
        user.setUserPass(passwordEncoder.encode(request.getUserPass()));
        user.setName(request.getName());

        //성별
        if (request.getUserGender().equals("")) {
            user.setUserGender("");
        }
        else {
            boolean gender = request.getUserGender().contains("남자") ||
                    request.getUserGender().contains("여자") ||
                    request.getUserGender().contains("중성");
            if (gender == true) {
                log.info("gender는 true");
            }
            if (gender) {
                user.setUserGender(request.getUserGender());
            } else {
                throw new Exception1();
            }
        }

        //지역
        if (request.getUserLocation().equals("")) {
            user.setUserLocation("");
        }
        else {
            boolean location = request.getUserLocation().contains("서울") ||
                    request.getUserLocation().contains("인천") ||
                    request.getUserLocation().contains("강원도") ||
                    request.getUserLocation().contains("경기도") ||
                    request.getUserLocation().contains("광주") ||
                    request.getUserLocation().contains("경상북도") ||
                    request.getUserLocation().contains("경상남도") ||
                    request.getUserLocation().contains("대구") ||
                    request.getUserLocation().contains("대전") ||
                    request.getUserLocation().contains("부산") ||
                    request.getUserLocation().contains("세종") ||
                    request.getUserLocation().contains("울산") ||
                    request.getUserLocation().contains("전라남도") ||
                    request.getUserLocation().contains("전라북도") ||
                    request.getUserLocation().contains("제주도") ||
                    request.getUserLocation().contains("충청남도") ||
                    request.getUserLocation().contains("충청북도");
            if (location) {
                user.setUserLocation(request.getUserLocation());
            } else {
                throw new Exception2();
            }
        }

        //전화번호
        user.setUserPhone(request.getUserPhone());

        //생년월일
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
            user.setUserPass(passwordEncoder.encode(request.getUserPass()));
        }
        if(request.getName() != null) {
            user.setName(request.getName());
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

    /*@Transactional(readOnly = true)
    public LoginResponse login(String userEmail, String userPass) {
        User user = userRepository.findById(userEmail).orElseThrow(EmailLoginFailedExc::new);
        if (!passwordEncoder.matches(userPass, user.getPassword())) {
            throw new EmailLoginFailedExc();
        }
        return new LoginResponse(user);
    }*/

}