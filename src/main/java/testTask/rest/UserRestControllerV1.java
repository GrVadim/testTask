package testTask.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import testTask.dto.AuthenticationRequestDto;
import testTask.dto.UserDto;
import testTask.entity.User;
import testTask.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user/")
public class UserRestControllerV1 {
    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findUserById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("register")
    public HttpStatus register(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();

            if (userService.findByUsername(username) != null) {
                throw new BadCredentialsException("User with username: " + username + " already exists");
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(requestDto.getPassword());
            userService.saveUser(user);


            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to register user");
        }
    }

    @PatchMapping("update/{id}")
    public HttpStatus update(@PathVariable(name = "id") Long id, @RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();

            User user = userService.findUserById(id);

            if (user == null) {
                throw new BadCredentialsException("User with username: " + username + " not found");
            }

            user.setUsername(requestDto.getUsername());
            user.setPassword(requestDto.getPassword());
            userService.saveUser(user);

            Map<Object, Object> response = new HashMap<>();
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to update user");
        }
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteUserById(@PathVariable(name = "id") Long id) {
        try {
            userService.deleteUser(id);

            Map<Object, Object> response = new HashMap<>();
            return HttpStatus.OK;
        } catch (Exception e) {
            throw new BadCredentialsException("Failed to delete user");
        }
    }
}
