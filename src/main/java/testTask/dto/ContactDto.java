package testTask.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.UniqueElements;
import testTask.entity.Contact;
import testTask.entity.Email;
import testTask.entity.Phone;

import java.util.HashSet;
import java.util.Set;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactDto {
    private Long id;
    private String name;
    private Set<String> phones = new HashSet<>();
    private Set<String> emails = new HashSet<>();

    public Contact toContact(){
        Contact contact = new Contact();
        contact.setId(id);
        contact.setName(name);
        contact.setEmails(new HashSet<>());
        contact.setPhones(new HashSet<>());
        if (phones != null &&  !phones.isEmpty()) {
            for (String phone: phones) {
                contact.getPhones().add(new Phone(phone));
            }
        }
        if (emails != null &&  !emails.isEmpty()) {
            for (String email: emails) {
                contact.getEmails().add(new Email(email));
            }
        }
        return contact;
    }

    public static ContactDto fromContact(Contact contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setName(contact.getName());

        Set<Phone> phones = contact.getPhones();
        Set<Email> emails = contact.getEmails();

        if (phones != null &&  !phones.isEmpty()) {
            for (Phone phone: phones) {
                contactDto.getPhones().add(phone.getPhone());
            }
        }
        if (emails != null &&  !emails.isEmpty()) {
            for (Email email: emails) {
                contactDto.getPhones().add(email.getEmail());
            }
        }

        return contactDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }
}
