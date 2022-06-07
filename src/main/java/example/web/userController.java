package example.web;


import example.model.User;
import example.model.userRequest;
import example.model.userResponse;
import example.service.UserService;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
public class userController {

    private final UserService service;

    @PostMapping("/join")
    public ResponseEntity<userResponse> create(@Valid @RequestBody userRequest request) {

        log.info("CREATE");

        if (ObjectUtils.isEmpty(request.getUserEmail()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getUserPass()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getUserName()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getUserGender()))
            request.setUserGender("");

        if (ObjectUtils.isEmpty(request.getUserLocation()))
            request.setUserLocation("");

        if (ObjectUtils.isEmpty(request.getUserPhone()))
            request.setUserPhone("");

        if (ObjectUtils.isEmpty(request.getUserBirth()))
            request.setUserBirth("");

        log.info("CREATE11");
        User result = this.service.add(request);
        return ResponseEntity.ok(new userResponse(result));
    }

    @GetMapping("/read/{userEmail}")
    public ResponseEntity<userResponse> readOne(@PathVariable  String userEmail) {
        log.info("READ ONE");
        User result = this.service.searchById(userEmail);
        return ResponseEntity.ok(new userResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<userResponse>> readAll() {
        log.info("READ ALL");
        List<User> list = this.service.searchAll();
        List<userResponse> response = list.stream().map(userResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update/{userEmail}")
    public ResponseEntity<User> update(@PathVariable String userEmail, @RequestBody userRequest request) {
        log.info("UPDATE");
        User result = this.service.updateById(userEmail, request);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{userEmail}")
    public ResponseEntity<?> deleteOne(@PathVariable String userEmail) {
        log.info("DELETE ONE");
        this.service.deleteById(userEmail);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        log.info("DELETE ALL");
        this.service.deleteAll();
        return ResponseEntity.ok().build();
    }
}