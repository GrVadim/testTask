package testTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import testTask.entity.Contact;
import testTask.repository.ContactRepository;
import testTask.repository.EmailRepository;
import testTask.repository.PhoneRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    EmailRepository emailRepository;
    @Autowired
    PhoneRepository phoneRepository;


    public Contact findContactById(Long contactId) {
        Optional<Contact> contactFromDb = contactRepository.findById(contactId);
        return contactFromDb.orElse(new Contact());
    }

    public List<Contact> allContacts() {
        return contactRepository.findAll();
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

    public boolean deleteContact(Long contactId) {
        if (contactRepository.findById(contactId).isPresent()) {
            contactRepository.deleteById(contactId);
            return true;
        }
        return false;
    }

    public List<Contact> ContactgtList(Long idMin) {
        return em.createQuery("SELECT u FROM Contact u WHERE u.id > :paramId", Contact.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
