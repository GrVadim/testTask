package testTask.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.entity.Contact;
import testTask.entity.Email;
import testTask.entity.Phone;
import testTask.repository.ContactRepository;
import testTask.repository.EmailRepository;
import testTask.repository.PhoneRepository;
import testTask.validator.EmailValidator;
import testTask.validator.PhoneValidator;


import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    PhoneRepository phoneRepository;


    public Contact findContactById(Long contactId, Long userId) {
        return  contactRepository.findByIdAndUserId(contactId, userId);
    }

    public List<Contact> listAllContacts(Long userId) {
        return contactRepository.findAllByUserId(userId);
    }

    public void deleteEmailsByContactId(Long contactId) {
        emailRepository.deleteEmailsByContactId(contactId);
    }

    public void deletePhonesByContactId(Long contactId) {
        phoneRepository.deletePhonesByContactId(contactId);
    }

    public void saveContact(Contact contact)  {
        validateContact(contact);
        contactRepository.save(contact);
    }

    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }

    public List<Contact> findByNameAndUserId(String name, Long userId) {
        return contactRepository.findByNameAndUserId(name, userId);
    }

    public boolean validateContact(Contact contact) {
        Set<String> setEmails = new HashSet<>();
        Set<String> setPhones = new HashSet<>();

        EmailValidator emailValidator = new EmailValidator();
        PhoneValidator phoneValidator = new PhoneValidator();

        for (Email email: contact.getEmails()) {
            if (!emailValidator.validateEmail(email.getEmail())) {
                throw new RuntimeException ("Email "+email.getEmail()+" not valid");
            }
            setEmails.add(email.getEmail());
        }

        for (Phone phone: contact.getPhones()) {
            if (!phoneValidator.validatePhone(phone.getPhone())) {
                throw new RuntimeException ("Phone "+phone.getPhone()+" not valid");
            }
            setPhones.add(phone.getPhone());
        }

        return true;
    }
}
