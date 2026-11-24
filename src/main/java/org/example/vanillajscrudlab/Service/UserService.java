package org.example.vanillajscrudlab.Service;

import org.example.vanillajscrudlab.DTO.UserRequest;
import org.example.vanillajscrudlab.DTO.UserResponse;
import org.example.vanillajscrudlab.Entity.User;
import org.example.vanillajscrudlab.Exceptions.DuplicateResourceException;
import org.example.vanillajscrudlab.Exceptions.ResourceNotFoundException;
import org.example.vanillajscrudlab.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create User
    @Transactional
    public UserResponse createPerson(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "Email already exists"
            );
        }

        User user = new User();

        user.setName(request.name());
        user.setEmail(request.email());


        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    // Read All Users
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Read One User
    @Transactional(readOnly = true)
    public UserResponse getUsersById(long id) {

        User user = findUserOrThrow(id);

        return mapToResponse(user);
    }

    // Update User
    @Transactional
    public UserResponse updateUser(long id, UserRequest request) {

        User user = findUserOrThrow(id);

        boolean userAlreadyExists =
                userRepository.existsByEmailAndIdNot(
                        request.email(), id
                );

        if (userAlreadyExists) {
            throw new DuplicateResourceException(
                    "User with this email already exists"
            );
        }

        user.setName(request.name());
        user.setEmail(request.email());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    // Delete User
    @Transactional
    public void deletePeople(long id) {

        User user = findUserOrThrow(id);

        userRepository.delete(user);
    }

    // Find User or Throw Exception
    private User findUserOrThrow(long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User with ID " + id + " not found"
                ));
    }

    // Map Entity to Response DTO
    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}


