/*package example.service;

import example.model.User;
import example.model.userRequest;
import example.service.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @Test
    void add() {
        when(this.todoRepository.save(any(User.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        userRequest expected = new userRequest();
        expected.setUserName("Test Name");

        User actual = this.todoService.add(expected);

        assertEquals(expected.getUserName(), actual.getUserName());
    }

    @Test
    void searchById() {
        User entity = new User();
        entity.setUserEmail("1234@1234.com");
        entity.setUserPass("12345678@");
        entity.setUserName("Name");
        entity.setUserGender("남");
        entity.setUserLocation("경기");
        entity.setUserPhone("12345678901");
        entity.setUserBirth("2001-04-13");
        Optional<User> optional = Optional.of(entity);

        given(this.todoRepository.findById(anyString()))
                .willReturn(optional);

                User actual = this.todoService.searchById("1234@1234.com");
        User expected =optional.get();
        assertEquals(expected.getUserEmail(), actual.getUserEmail());
        assertEquals(expected.getUserPass(), actual.getUserPass());
        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getUserGender(), actual.getUserGender());
        assertEquals(expected.getUserLocation(), actual.getUserLocation());
        assertEquals(expected.getUserPhone(), actual.getUserPhone());
        assertEquals(expected.getUserBirth(), actual.getUserBirth());


    }

    @Test
    public void searchByIdFailed() {
        given(this.todoRepository.findById(anyString()))
                .willReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            this.todoService.searchById("1234@1234.com");
        });
    }
}*/