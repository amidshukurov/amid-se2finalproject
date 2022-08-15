package az.phonebook.controller;


import az.phonebook.dto.UserOperation;
import az.phonebook.entity.UserEntity;
import az.phonebook.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/status")
    public ResponseEntity<List<UserEntity>> status() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<UserEntity>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/user/add")
    public ResponseEntity<UserOperation> addUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.postUser(user));
    }

    @PutMapping("/user/edit")
    public ResponseEntity<UserOperation> editUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.editUser(user));
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<UserOperation> deleteUser(@RequestParam Long userId) {
        return ResponseEntity.ok(userService.deleteUser(userId));
    }


}
