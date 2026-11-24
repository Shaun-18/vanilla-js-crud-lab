package org.example.vanillajscrudlab.Controller;


import jakarta.validation.Valid;
import org.example.vanillajscrudlab.DTO.UserRequest;
import org.example.vanillajscrudlab.DTO.UserResponse;
import org.example.vanillajscrudlab.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Create User
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request
    ) {
        UserResponse response = userService.createPerson(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Get All Users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUsersByID(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.getUsersById(id));

    }

    //Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(
            @PathVariable long id
    ) {
        userService.deletePeople(id);
        return ResponseEntity.noContent().build();
    }
}
