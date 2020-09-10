package testTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.entity.Email;
import testTask.entity.Phone;

import java.util.Set;

public interface EmailRepository extends JpaRepository<Email, Long> {
//    Set<Email> findByContactId(Long id);
//    void deleteEmailsByContactId(Long id);
}
