package testTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.entity.Contact;
import testTask.entity.User;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByNameAndUserId(String name, Long userId);
    Contact findByIdAndUserId(Long id, Long userId);
    List<Contact> findAllByUserId(Long userId);
}
