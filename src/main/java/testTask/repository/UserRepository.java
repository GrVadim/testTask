package testTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
