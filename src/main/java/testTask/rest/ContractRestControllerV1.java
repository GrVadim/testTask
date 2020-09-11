package testTask.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import testTask.dto.ContactDto;
import testTask.dto.UserDto;
import testTask.entity.Contact;
import testTask.entity.Email;
import testTask.entity.Phone;
import testTask.entity.User;
import testTask.service.ContactService;

import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * REST controller user connected requestst.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@RestController
@RequestMapping(value = "/contact/")
public class ContractRestControllerV1 {

    private final ContactService contactService;

    public ContractRestControllerV1(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ContactDto> getUserById(@PathVariable(name = "id") Long id){
        Contact contact = contactService.findContactById(id);

        if(contact == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ContactDto result = ContactDto.fromContact(contact);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("add")
    public HttpStatus addContact(@RequestBody ContactDto ContactDto) {
        try {
            contactService.saveContact(ContactDto.toContact());
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to register user");
        }
    }

    @PatchMapping("update/{id}")
    public HttpStatus update(@PathVariable(name = "id") Long id, @RequestBody ContactDto contactDto) {
        try {
            contactService.clearContactEmails(id);
            contactService.clearContactPhones(id);

            Contact contact = contactService.findContactById(id);

            if (contact == null) {
                throw new BadCredentialsException("Contact with id: " + id + " not found");
            }
            Set<String> phones = contactDto.getPhones();
            Set<String> emails = contactDto.getEmails();

            contact.setName(contactDto.getName());
            contact.getPhones().clear();
            contact.getEmails().clear();

            contact.setEmails(new HashSet<>());
            contact.setPhones(new HashSet<>());

            if (phones != null &&  !phones.isEmpty()) {
                for (String phone: phones) {
                    contact.getPhones().add(new Phone(phone));
                }
            } else {
                contact.setPhones(new HashSet<>());
            }

            if (emails != null &&  !emails.isEmpty()) {
                for (String email: emails) {
                    contact.getEmails().add(new Email(email));
                }
            }else {
                contact.setEmails(new HashSet<>());
            }

            contactService.saveContact(contact);

            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to update user");
        }
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteUserById(@PathVariable(name = "id") Long id) {
        try {
            contactService.deleteContact(id);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to delete user");
        }
    }
}
