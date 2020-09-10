package testTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.entity.Contact;
import testTask.entity.User;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    User findByName(String username);
}
