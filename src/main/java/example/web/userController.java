package example.web;

import example.CustomUserDetailsService;
import example.JwtUtil;
import example.exception.UserNotFoundExc;
import example.model.User;
import example.model.userRequest;
import example.model.userResponse;
import example.service.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin
@AllArgsConstructor
@RestController
@Api
@RequestMapping("/")
public class userController {

    private final UserService service;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<userResponse> create(
            @ApiParam(value = "이메일") @RequestParam String userEmail,
            @ApiParam(value = "비밀번호") @RequestParam String userPass,
            @ApiParam(value = "이름") @RequestParam String name,
            @ApiParam(value = "성별") @RequestParam String userGender,
            @ApiParam(value = "지역") @RequestParam String userLocation,
            @ApiParam(value = "전화번호") @RequestParam String userPhone,
            @ApiParam(value = "생년월일") @RequestParam String userBirth,
            @Valid @RequestBody userRequest request){

        log.info("CREATE");

        if (ObjectUtils.isEmpty(request.getUserEmail()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getUserPass()))
            return ResponseEntity.badRequest().build();

        if (ObjectUtils.isEmpty(request.getName()))
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

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "로그인 성공",
                    required = true, dataType = "String", paramType = "header")
    })
    @GetMapping("/read/{userEmail}")
    @ApiOperation(value = "회원 조회")
    public ResponseEntity<userResponse> readOne(
            @ApiParam(value = "이메일", required = true) @PathVariable  String userEmail){
        log.info("READ ONE");
        User result = this.service.searchById(userEmail);
        return ResponseEntity.ok(new userResponse(result));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "로그인 성공",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation("회원 목록 조회")
    @GetMapping
    public ResponseEntity<List<userResponse>> readAll() {
        log.info("READ ALL");
        List<User> list = this.service.searchAll();
        List<userResponse> response = list.stream().map(userResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "로그인 성공",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정")
    @PatchMapping("/update/{userEmail}")
    public ResponseEntity<User> update(@PathVariable String userEmail,
            @ApiParam(value = "비밀번호") @RequestParam String userPass,
            @ApiParam(value = "이름") @RequestParam String name,
            @ApiParam(value = "성별") @RequestParam String userGender,
            @ApiParam(value = "지역") @RequestParam String userLocation,
            @ApiParam(value = "전화번호") @RequestParam String userPhone,
            @ApiParam(value = "생년월일") @RequestParam String userBirth,
            @RequestBody userRequest request) {
        log.info("UPDATE");
        User result = this.service.updateById(userEmail, request);
        return ResponseEntity.ok(result);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    value = "로그인 성공",
                    required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제")
    @DeleteMapping("/delete/{userEmail}")
    public ResponseEntity<?> deleteOne(
            @ApiParam(value = "회원 이메일", required = true)@PathVariable String userEmail) {
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

    @ApiOperation("로그인")
    @GetMapping("/login")
    public ResponseEntity<LoginSuccessResponse> login(
            @RequestBody LoginRequest loginRequest,
            @ApiParam(value = "로그인 이메일", required = true) @RequestParam String userEmail,
            @ApiParam(value = "로그인 비밀번호", required = true) @RequestParam String userPass) {
        log.info("LOGIN");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserEmail(), loginRequest.getUserPass()));
        }
        catch (UserNotFoundExc e) {
            throw new UserNotFoundExc("");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserEmail());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginSuccessResponse(token));
    }

    @AllArgsConstructor
    @Data
    static class LoginRequest{
        private String userEmail;
        private String userPass;
    }
    @AllArgsConstructor
    @Data
    static class LoginSuccessResponse {
        private String token;
    }
}