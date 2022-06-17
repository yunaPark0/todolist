package example.service.repository;

import example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByUserEmail(String userEmail);
    boolean existsByUserEmail(String userEmail);
}
