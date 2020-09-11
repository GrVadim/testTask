package testTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testTask.entity.Phone;

import java.util.Set;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Set<Phone> findByContactId(Long id);
    void deletePhonesByContactId(Long id);
}
