package testTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.entity.Contact;
import testTask.repository.ContactRepository;
import testTask.repository.EmailRepository;
import testTask.repository.PhoneRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    PhoneRepository phoneRepository;


    @Transactional
    public Contact findContactById(Long contactId, Long userId) {
        return  contactRepository.findByIdAndUserId(contactId, userId);
    }

    @Transactional
    public List<Contact> listAllContacts(Long userId) {
        return contactRepository.findAllByUserId(userId);
    }

    @Transactional
    public boolean clearContactEmails(Long contactId) {
        emailRepository.deleteEmailsByContactId(contactId);
        return true;
    }

    @Transactional
    public boolean clearContactPhones(Long contactId) {
        phoneRepository.deletePhonesByContactId(contactId);
        return true;
    }

    @Transactional
    public boolean saveContact(Contact contact) {
        contactRepository.save(contact);
        return true;
    }

    @Transactional
    public boolean deleteContact(Long contactId, Long userId) {
        if (contactRepository.findByIdAndUserId(contactId, userId) != null) {
            contactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }

    public List<Contact> findByNameAndUserId(String name, Long userId) {
        return contactRepository.findByNameAndUserId(name, userId);
    }
}
