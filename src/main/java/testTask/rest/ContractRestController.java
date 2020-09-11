package testTask.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import testTask.dto.ContactDto;
import testTask.entity.Contact;
import testTask.entity.Email;
import testTask.entity.Phone;
import testTask.entity.User;
import testTask.service.ContactService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Validated
@RestController
@RequestMapping(value = "/contact/")
public class ContractRestController {

    @Autowired
    private ContactService contactService;

    public ContractRestController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<ContactDto> getUserById(@PathVariable(name = "id") Long id){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Contact contact = contactService.findContactById(id, currentUser.getId());

        if(contact == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        ContactDto result = ContactDto.fromContact(contact);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "listContacts")
    public ResponseEntity<List<ContactDto>> getListUsers() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Contact> contacts = contactService.listAllContacts(currentUser.getId());

        if (contacts == null || contacts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<ContactDto> contactDtos = new ArrayList<>();

        for (Contact contact : contacts) {
            contactDtos.add(ContactDto.fromContact(contact));
        }

        return new ResponseEntity<>(contactDtos, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity addContact(@RequestBody ContactDto ContactDto) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            List<Contact> duplicates = contactService.findByNameAndUserId(ContactDto.getName(), currentUser.getId());

            if (duplicates != null && !duplicates.isEmpty()) {
                return new ResponseEntity<>("Contact with name: " + ContactDto.getName() + " already exists", HttpStatus.BAD_REQUEST);
            }

            Contact contract = ContactDto.toContact();
            contract.setUserId(currentUser.getId());

            contactService.saveContact(contract);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to add contact");
        }
    }

    @PatchMapping("update/{id}")
    public HttpStatus update(@PathVariable(name = "id") Long id, @RequestBody @Valid ContactDto contactDto) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Contact contact = contactService.findContactById(id, currentUser.getId());

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
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Contact contact = contactService.findContactById(id, currentUser.getId());

            if (contact == null) {
                throw new BadCredentialsException("Contact with id: " + id + " not found");
            }

            contactService.deleteContact(id);
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to delete user");
        }
    }
}
