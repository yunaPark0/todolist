/*package example.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.model.User;
import example.model.userRequest;
import example.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(userController.class)
class userControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TodoService todoService;

    private User expected;

    @BeforeEach
    void setup() {
        this.expected = new User();
        this.expected.setUserEmail("1234@1234.com");
        this.expected.setUserPass("12345678@");
        this.expected.setUserName("Name");
        this.expected.setUserGender("남");
        this.expected.setUserLocation("경기");
        this.expected.setUserPhone("12345678901");
        this.expected.setUserBirth("2001-04-13");
    }

    @Test
    void create() throws Exception{
        when(this.todoService.add(any(userRequest.class)))
                .then((i) -> {
                    userRequest request = i.getArgument(0, userRequest.class);
                    return new User(this.expected.getUserEmail(),
                            request.getUserName(),
                            this.expected.getUserPass(),
                            this.expected.getUserGender(),
                            this.expected.getUserLocation(),
                            this.expected.getUserPhone(),
                            this.expected.getUserBirth()
                    );
                });

        userRequest request = new userRequest();
        request.setUserName("ANY Name");

        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(request);

        this.mvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ANY TITLE"));
    }

    @Test
    void readOne() {
    }
}*/